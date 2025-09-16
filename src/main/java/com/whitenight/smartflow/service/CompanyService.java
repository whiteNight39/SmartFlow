package com.whitenight.smartflow.service;

import com.whitenight.smartflow.mapper.CompanyRequestMapper;
import com.whitenight.smartflow.mapper.StaffInviteRequestMapper;
import com.whitenight.smartflow.mapper.StaffRequestMapper;
import com.whitenight.smartflow.model.entity.Company;
import com.whitenight.smartflow.model.entity.Staff;
import com.whitenight.smartflow.model.entity.StaffInvite;
import com.whitenight.smartflow.model.request.*;
import com.whitenight.smartflow.model.response.BaseResponse;
import com.whitenight.smartflow.model.response.StaffAccountDetails;
import com.whitenight.smartflow.repository.database.interfaces.CompanyRepository;
import com.whitenight.smartflow.repository.database.interfaces.StaffInviteRepository;
import com.whitenight.smartflow.repository.database.interfaces.StaffRepository;
import com.whitenight.smartflow.utils.TokenGenerator;
import com.whitenight.smartflow.utils.exception.ApiException;
import com.whitenight.smartflow.utils.rank.StaffRole;
import com.whitenight.smartflow.utils.validator.CompanyValidator;
import com.whitenight.smartflow.utils.validator.StaffValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyRequestMapper companyRequestMapper;

    private final StaffInviteRepository staffInviteRepository;
    private final StaffInviteRequestMapper staffInviteRequestMapper;

    private final StaffRepository staffRepository;
    private final StaffRequestMapper staffRequestMapper;

    private final MailService mailService;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, CompanyRequestMapper companyRequestMapper, StaffInviteRepository staffInviteRepository, StaffInviteRequestMapper staffInviteRequestMapper, StaffRepository staffRepository, StaffRequestMapper staffRequestMapper, MailService mailService) {
        this.companyRepository = companyRepository;
        this.companyRequestMapper = companyRequestMapper;
        this.staffInviteRepository = staffInviteRepository;
        this.staffInviteRequestMapper = staffInviteRequestMapper;
        this.staffRepository = staffRepository;
        this.staffRequestMapper = staffRequestMapper;
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

        StaffInviteCreateRequest inviteCreateRequest = StaffInviteCreateRequest.builder()
                .staffInviteCSVUploadId(null)
                .staffInviteEmail(request.getCompanyContactPersonEmail())
                .staffInviteToken(TokenGenerator.generateSecureToken(16))
                .staffInviteExpiresAt(LocalDateTime.now().plusDays(2))
                .build();
        StaffInvite invite = staffInviteRequestMapper.toEntity(inviteCreateRequest);
        staffInviteRepository.inviteStaff(invite);

        // Generate email and send to request.getCompanyContactPersonEmail()
        mailService.sendActivationEmail(invite.getStaffInviteEmail(), invite.getStaffInviteToken());

        StaffCreateRequest staffCreateRequest = StaffCreateRequest.builder()
                .staffFirstName(request.getCompanyContactPersonFirstName())
                .staffLastName(request.getCompanyContactPersonLastName())
                .staffEmail(request.getCompanyContactPersonEmail())
                .staffPhone(request.getCompanyContactPersonPhone())
                .staffRole("SUPER_ADMIN")
                .staffJobTitle(request.getCompanyContactPersonJobTitle())
                .build();
        Staff staff = staffRequestMapper.toEntity(staffCreateRequest);
        staff.setStaffWhoAddedId(devId);
        staff.setStaffCompanyId(company.getCompanyId());
        String tempPassword = TokenGenerator.generateSecureToken(12); // or any random string
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(tempPassword); // Use BCrypt or whatever you're using
        staff.setStaffPassword(hashedPassword);

        staffRepository.addStaff(staff);
    }

    public void updateCompanyDetails(CompanyUpdateRequest request, UUID staffId, UUID companyId, String role) {

        if (request == null) throw new IllegalArgumentException("Company request cannot be null");

        Company staffCompany = companyRepository.getCompany(companyId);
        CompanyValidator.validateCompany(staffCompany);

        Staff staff = staffRepository.getStaffById(staffId);
        StaffValidator.validateStaff(staff);
        if (!role.equalsIgnoreCase("SUPER_ADMIN")) {
            throw new IllegalArgumentException("Access denied. Required role: SUPER_ADMIN");
        }

        Company companyUpdated = companyRequestMapper.toEntity(request);
        companyUpdated.setCompanyId(companyId);
        companyRepository.updateCompany(companyUpdated);
    }

    public void updateCompanyContactPerson(CompanyContactUpdateRequest request, UUID staffId, UUID companyId, String role) {
        if (request == null) throw new IllegalArgumentException("Company request cannot be null");

        Company staffCompany = companyRepository.getCompany(companyId);
        CompanyValidator.validateCompany(staffCompany);

        Staff staffAdding = staffRepository.getStaffById(staffId);
        StaffValidator.validateStaff(staffAdding);
        if (!role.equalsIgnoreCase("SUPER_ADMIN")) {
            throw new IllegalArgumentException("Access denied. Required role: SUPER_ADMIN");
        }

        String staffAddedEmail = request.getCompanyContactPersonEmail();
        UUID staffAddedId = staffRepository.getStaffByEmail(staffAddedEmail).getStaffId();
        Staff staffAdded = staffRepository.getStaffById(staffAddedId);
        StaffValidator.validateStaff(staffAdded);

        if (staffAddedId.equals(staffId)) {
            throw new IllegalArgumentException("New contact person must be different from the current one");
        }

        Company companyUpdated = companyRequestMapper.toEntity(request);
        companyUpdated.setCompanyId(companyId);
        companyRepository.updateCompany(companyUpdated);

        staffAdded.setStaffRole("SUPER_ADMIN");
        staffRepository.updateStaff(staffAdded);
        staffAdding.setStaffRole("ADMIN");
        staffRepository.updateStaff(staffAdding);
    }

    public List<Company> getAllCompanies(UUID devId) {
        if (devId == null) throw new IllegalArgumentException("devId cannot be null");

        Staff dev = staffRepository.getStaffById(devId);
        StaffValidator.validateStaff(dev);
        StaffValidator.validateRoles(dev, "DEVELOPER");

        return companyRepository.getAllCompanies();
    }

    public void unregisterCompany(UUID companyId, UUID devId) {
        if (companyId == null) throw new IllegalArgumentException("Company Id is required");

        Staff dev = staffRepository.getStaffById(devId);
        StaffValidator.validateStaff(dev);
        StaffValidator.validateRoles(dev, "DEVELOPER");

        Company company = companyRepository.getCompany(companyId);
        CompanyValidator.validateCompany(company);

        companyRepository.unregisterCompany(companyId);
    }
}
