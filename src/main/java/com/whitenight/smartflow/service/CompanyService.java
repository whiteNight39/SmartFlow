package com.whitenight.smartflow.service;

import com.whitenight.smartflow.mapper.CompanyRequestMapper;
import com.whitenight.smartflow.mapper.StaffInviteRequestMapper;
import com.whitenight.smartflow.model.entity.Company;
import com.whitenight.smartflow.model.entity.Staff;
import com.whitenight.smartflow.model.entity.StaffInvite;
import com.whitenight.smartflow.model.enums.Status;
import com.whitenight.smartflow.model.request.*;
import com.whitenight.smartflow.model.response.BaseResponse;
import com.whitenight.smartflow.repository.database.interfaces.CompanyRepository;
import com.whitenight.smartflow.repository.database.interfaces.StaffInviteRepository;
import com.whitenight.smartflow.repository.database.interfaces.StaffRepository;
import com.whitenight.smartflow.utils.TokenGenerator;
import com.whitenight.smartflow.utils.exception.ApiException;
import com.whitenight.smartflow.utils.rank.StaffRole;
import com.whitenight.smartflow.utils.validator.CompanyValidator;
import com.whitenight.smartflow.utils.validator.StaffValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyRequestMapper companyRequestMapper;

    private final StaffInviteRepository staffInviteRepository;
    private final StaffInviteRequestMapper staffInviteRequestMapper;

    private final StaffRepository staffRepository;

    private final MailService mailService;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, CompanyRequestMapper companyRequestMapper, StaffInviteRepository staffInviteRepository, StaffInviteRequestMapper staffInviteRequestMapper, StaffRepository staffRepository, MailService mailService) {
        this.companyRepository = companyRepository;
        this.companyRequestMapper = companyRequestMapper;
        this.staffInviteRepository = staffInviteRepository;
        this.staffInviteRequestMapper = staffInviteRequestMapper;
        this.staffRepository = staffRepository;
        this.mailService = mailService;
    }

    public BaseResponse<?> onboardCompany(CompanyCreateRequest request, UUID devId) {
        if (request == null) throw new ApiException("11", "Company request cannot be null", null);

        Staff dev = staffRepository.getStaffById(devId);
        StaffValidator.validateStaff(dev);
        StaffValidator.validateRoles(dev, StaffRole.DEVELOPER);

        Company duplicateCompany = companyRepository.getCompanyByRegistrationNumber(request.getCompanyRegistrationNumber());
        if (duplicateCompany != null)
            throw new ApiException("55", "Company with registration number "+ request.getCompanyRegistrationNumber() +" already exists", null);

        Company company = companyRequestMapper.toEntity(request);
        companyRepository.addCompany(company);

        String token = TokenGenerator.generateSecureToken(16);
        StaffInviteCreateRequest inviteCreateRequest = StaffInviteCreateRequest.builder()
                .staffInviteCSVUploadId(null)
                .staffInviteEmail(request.getCompanyContactEmail())
                .staffInviteToken(token)
                .staffInviteExpiresAt(Instant.now().plus(2, ChronoUnit.DAYS))
                .build();
        StaffInvite invite = staffInviteRequestMapper.toEntity(inviteCreateRequest);
        staffInviteRepository.inviteStaff(invite);

        // Generate email and send to request.getCompanyContactEmail()
        mailService.sendActivationEmail(request.getCompanyContactEmail(), token);

        Staff staff = Staff.builder()
                .staffFirstName(request.getCompanyContactFirstName())
                .staffLastName(request.getCompanyContactLastName())
                .staffEmail(request.getCompanyContactEmail())
                .staffPhone(request.getCompanyContactPhone())
                .staffRole(StaffRole.SUPER_ADMIN)
                .staffJobTitle(request.getCompanyContactJobTitle())
                .staffActivated(false)
                .staffWhoAddedId(devId)
                .staffCompanyId(company.getCompanyId())
                .staffPassword(null)
                .build();
        staffRepository.addStaff(staff);

        return new BaseResponse<>("00", "Company onboarded, awaiting activation", null);
    }

    @Transactional
    public BaseResponse<?> activateCompany(CompanyActivationRequest request) {
        if (request == null) throw new ApiException("11", "Request cannot be null", null);

        Company company = companyRepository.getCompanyByRegistrationNumber(request.getCompanyRegistrationNumber());
        CompanyValidator.validateCompany(company);
        if (company.getCompanyStatus() == Status.ACTIVE) {
            throw new ApiException("55", "Company already activated", null);
        }

        List<StaffInvite> staffInvites = companyRepository.getCompanyContactInviteDetails(request.getCompanyRegistrationNumber());
        StaffInvite matchedInvite = staffInvites.stream()
                .filter(inv -> request.getToken().equals(inv.getStaffInviteToken()))
                .findFirst()
                .orElseThrow(() -> new ApiException("55", "Invalid token", null));

        if (matchedInvite.getStaffInviteExpiresAt().isBefore(Instant.now())) {
            throw new ApiException("55", "Token expired", null);
        }

        Staff companyContact = staffRepository.getStaffByEmail(request.getCompanyContactEmail());
        companyContact.setStaffActivated(true);
        companyContact.setStaffStatus(Status.ACTIVE);
        staffRepository.updateStaff(companyContact);

        company.setCompanyStatus(Status.ACTIVE);
        companyRepository.updateCompany(company);

        // clear invites after activation
        for (StaffInvite staffInvite : staffInvites) {
            staffInviteRepository.deleteStaffInvite(staffInvite.getStaffInviteId());
        }

        return new BaseResponse<>("00", "Company activated", null);
    }

    public BaseResponse<?> updateCompanyDetails(CompanyUpdateRequest request, UUID staffId, UUID companyId, String role) {
        if (request == null) throw new ApiException("11", "Validation failed: Company update request cannot be null", null);

        Company staffCompany = companyRepository.getCompany(companyId);
        CompanyValidator.validateCompany(staffCompany);

        Staff staff = staffRepository.getStaffById(staffId);
        StaffValidator.validateStaff(staff);
        StaffValidator.validateRoles(staff, StaffRole.SUPER_ADMIN);

        Company companyUpdated = companyRequestMapper.toEntity(request);
        companyUpdated.setCompanyId(companyId);

        try {
            companyRepository.updateCompany(companyUpdated);
        } catch (Exception e) {
            throw new ApiException("22", "Failed to update company details", e);
        }

        return new BaseResponse<>("00", "Company updated", null);
    }

    @Transactional
    public BaseResponse<?> updateCompanyContact(CompanyUpdateRequest request, UUID staffId, UUID companyId, String role) {
        if (request == null) {
            throw new ApiException("11", "Company update request cannot be null", null);
        }

        Company staffCompany = companyRepository.getCompany(companyId);
        CompanyValidator.validateCompany(staffCompany);

        Staff staffAdding = staffRepository.getStaffById(staffId);
        StaffValidator.validateStaff(staffAdding);
        StaffValidator.validateRoles(staffAdding, StaffRole.SUPER_ADMIN);

        Staff staffAdded = staffRepository.getStaffByEmail(request.getCompanyContactEmail());
        StaffValidator.validateStaff(staffAdded);

        if (staffAdded.getStaffId().equals(staffId)) {
            throw new ApiException("44", "New contact cannot be the same as the current contact", null);
        }

        Company companyUpdated = companyRequestMapper.toEntity(request);
        companyUpdated.setCompanyId(companyId);
        companyRepository.updateCompany(companyUpdated);

        staffAdded.setStaffRole(StaffRole.SUPER_ADMIN);
        staffRepository.updateStaff(staffAdded);

        staffAdding.setStaffRole(StaffRole.ADMIN);
        staffRepository.updateStaff(staffAdding);

        return new BaseResponse<>("00", "Company contact person changed", null);
    }

    public BaseResponse<?> getAllCompanies(UUID devId) {
        if (devId == null) {
            throw new ApiException("11", "Developer ID cannot be null", null);
        }

        Staff dev = staffRepository.getStaffById(devId);
        StaffValidator.validateStaff(dev);
        StaffValidator.validateRoles(dev, StaffRole.DEVELOPER);

        List<Company> companies = companyRepository.getAllCompanies();

        return new BaseResponse<>("00", "Companies retrieved successfully", companies);
    }

    @Transactional
    public BaseResponse<?> unregisterCompany(UUID companyId, UUID devId) {
        if (companyId == null) throw new ApiException("11", "Company ID cannot be null", null);

        Staff dev = staffRepository.getStaffById(devId);
        StaffValidator.validateStaff(dev);
        StaffValidator.validateRoles(dev, StaffRole.DEVELOPER);

        Company company = companyRepository.getCompany(companyId);
        CompanyValidator.validateCompany(company);

        companyRepository.unregisterCompany(companyId);

        return new BaseResponse<>("00", "Company unregistered successfully", null);
    }
}
