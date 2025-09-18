package com.whitenight.smartflow.service;

import com.whitenight.smartflow.mapper.StaffInviteRequestMapper;
import com.whitenight.smartflow.mapper.StaffJwtAuthMapper;
import com.whitenight.smartflow.mapper.StaffRequestMapper;
import com.whitenight.smartflow.mapper.StaffUpdateRequestMapper;
import com.whitenight.smartflow.model.entity.Staff;
import com.whitenight.smartflow.model.entity.StaffInvite;
import com.whitenight.smartflow.model.entity.StaffJwtAuth;
import com.whitenight.smartflow.model.entity.StaffUpdate;
import com.whitenight.smartflow.model.enums.Status;
import com.whitenight.smartflow.model.request.StaffCreateRequest;
import com.whitenight.smartflow.model.request.StaffInviteCreateRequest;
import com.whitenight.smartflow.model.request.StaffUpdateRequest;
import com.whitenight.smartflow.model.response.BaseResponse;
import com.whitenight.smartflow.repository.database.interfaces.StaffInviteRepository;
import com.whitenight.smartflow.repository.database.interfaces.StaffJwtAuthRepository;
import com.whitenight.smartflow.repository.database.interfaces.StaffRepository;
import com.whitenight.smartflow.repository.database.interfaces.StaffUpdateRepository;
import com.whitenight.smartflow.utils.TokenGenerator;
import com.whitenight.smartflow.utils.exception.ApiException;
import com.whitenight.smartflow.utils.jwt.JwtUtil;
import com.whitenight.smartflow.utils.rank.RoleUtils;
import com.whitenight.smartflow.utils.rank.StaffRole;
import com.whitenight.smartflow.utils.validator.StaffValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final StaffRequestMapper staffRequestMapper;
    private final StaffUpdateRequestMapper staffUpdateRequestMapper;
    private final StaffUpdateRepository staffUpdateRepository;

    private final StaffInviteRepository staffInviteRepository;
    private final StaffInviteRequestMapper staffInviteRequestMapper;

    private final StaffJwtAuthRepository staffJwtAuthRepository;
    private final StaffJwtAuthMapper staffJwtAuthMapper;

    private final JwtUtil jwtUtil;

    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;


    public StaffService(StaffRepository staffRepository, StaffJwtAuthRepository staffJwtAuthRepository, StaffJwtAuthMapper staffJwtAuthMapper, JwtUtil jwtUtil, StaffRequestMapper staffRequestMapper, StaffUpdateRequestMapper staffUpdateRequestMapper, StaffUpdateRepository staffUpdateRepository, StaffInviteRepository staffInviteRepository, StaffInviteRequestMapper staffInviteRequestMapper, MailService mailService, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.staffJwtAuthRepository = staffJwtAuthRepository;
        this.staffJwtAuthMapper = staffJwtAuthMapper;
        this.jwtUtil = jwtUtil;
        this.staffRequestMapper = staffRequestMapper;
        this.staffUpdateRequestMapper = staffUpdateRequestMapper;
        this.staffUpdateRepository = staffUpdateRepository;
        this.staffInviteRepository = staffInviteRepository;
        this.staffInviteRequestMapper = staffInviteRequestMapper;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public BaseResponse<?> devSignIn(String devEmail, String devPassword, String ip, String userAgent) {
        if (devEmail == null) throw new ApiException("11", "Developer email cannot be null", null);
        if (devPassword == null) throw new ApiException("11", "Developer password cannot be null", null);

        Staff dev = staffRepository.getStaffByEmail(devEmail);
        if (dev == null) throw new ApiException("33", "Invalid email or password", null);

        StaffValidator.validateRoles(dev, StaffRole.DEVELOPER);

        if (!passwordEncoder.matches(devPassword, dev.getStaffPassword()))
            throw new ApiException("33", "Invalid email or password", null);


        StaffJwtAuth auth = staffJwtAuthRepository.getStaffJwtAuth(dev.getStaffId(), ip, userAgent);
        String token;

        if (auth == null || jwtUtil.isTokenExpired(auth.getStaffJwtAuthJwtToken())) {
            token = jwtUtil.generateToken(dev.getStaffId());

            StaffJwtAuth staffJwtAuth = StaffJwtAuth.builder()
                    .staffJwtAuthStaffId(dev.getStaffId())
                    .staffJwtAuthJwtToken(token)
                    .staffJwtAuthIssuedAt(jwtUtil.getIssueDate(token))
                    .staffJwtAuthExpiresAt(jwtUtil.getExpirationDate(token))
                    .staffJwtAuthUserDeviceIp(ip)
                    .staffJwtAuthUserDeviceAgent(userAgent)
                    .staffJwtAuthLevel(dev.getStaffRole())
                    .build();

            staffJwtAuthRepository.createStaffJwtAuth(staffJwtAuth);
        } else {
            token = auth.getStaffJwtAuthJwtToken();
        }

        return new BaseResponse<>("00", "Sign-in successful", token);
    }

    @Transactional
    public BaseResponse<?> staffSignIn(String staffEmail, String staffPassword, String ip, String userAgent) {
        if (staffEmail == null) throw new ApiException("11", "Staff email cannot be null", null);
        if (staffPassword == null) throw new ApiException("11", "Staff password cannot be null", null);

        Staff staff = staffRepository.getStaffByEmail(staffEmail);
        if (staff == null) throw new ApiException("33", "Invalid email or password", null);

        if (!passwordEncoder.matches(staffPassword, staff.getStaffPassword()))
            throw new ApiException("33", "Invalid email or password", null);


        StaffJwtAuth auth = staffJwtAuthRepository.getStaffJwtAuth(staff.getStaffId(), ip, userAgent);
        String token;

        if (auth == null || jwtUtil.isTokenExpired(auth.getStaffJwtAuthJwtToken())) {
            token = jwtUtil.generateToken(staff.getStaffId());

            StaffJwtAuth staffJwtAuth = StaffJwtAuth.builder()
                    .staffJwtAuthStaffId(staff.getStaffId())
                    .staffJwtAuthJwtToken(token)
                    .staffJwtAuthIssuedAt(jwtUtil.getIssueDate(token))
                    .staffJwtAuthExpiresAt(jwtUtil.getExpirationDate(token))
                    .staffJwtAuthUserDeviceIp(ip)
                    .staffJwtAuthUserDeviceAgent(userAgent)
                    .staffJwtAuthLevel(staff.getStaffRole())
                    .build();

            staffJwtAuthRepository.createStaffJwtAuth(staffJwtAuth);
        } else {
            token = auth.getStaffJwtAuthJwtToken();
        }

        return new BaseResponse<>("00", "Sign-in successful", token);
    }

//    @Transactional
    public BaseResponse<?> addSingleStaff(StaffCreateRequest request, UUID staffId, UUID companyId, StaffRole role) {
        if (request == null) throw new ApiException("11", "Staff request cannot be null", null);
        if (staffId == null) throw new ApiException("11", "Staff ID is required", null);

        Staff staffAdding = staffRepository.getStaffById(staffId);
        StaffValidator.validateStaff(staffAdding);

        // Prevent duplicate staff by email
        if (staffRepository.getStaffByEmail(request.getStaffEmail()) != null) {
            throw new ApiException("44", "A staff with this email already exists", null);
        }

        Staff staffAdded = staffRequestMapper.toEntity(request);
        staffAdded.setStaffPassword(null);
        staffAdded.setStaffWhoAddedId(staffId);
        staffAdded.setStaffCompanyId(companyId);
        staffAdded.setStaffDepartmentHeadId(
                staffRepository.getStaffDepartmentHeadId(companyId, request.getStaffDepartment())
        );

        StaffRole addedRole = request.getStaffRole();

        // Role hierarchy check
        if (!RoleUtils.canAdd(role, addedRole)) {
            throw new ApiException("33", "You are not authorized to add staff with role: " + addedRole, null);
        }

        // Persist staff first
        staffRepository.addStaff(staffAdded);

        // Create invite
        StaffInviteCreateRequest inviteCreateRequest = StaffInviteCreateRequest.builder()
                .staffInviteCSVUploadId(null)
                .staffInviteEmail(request.getStaffEmail())
                .staffInviteToken(TokenGenerator.generateSecureToken(16))
                .staffInviteExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .build();

        StaffInvite invite = staffInviteRequestMapper.toEntity(inviteCreateRequest);
        staffInviteRepository.inviteStaff(invite);

        try {
            mailService.sendActivationEmail(invite.getStaffInviteEmail(), invite.getStaffInviteToken());
        } catch (Exception e) {
            // log the failure, but don't break the transaction
            log.error("Failed to send activation email to {}", invite.getStaffInviteEmail(), e);
        }

        return new BaseResponse<>("00", "Staff added successfully. Activation email sent.", null);
    }

    private static final Logger log = LoggerFactory.getLogger(StaffService.class);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BaseResponse<?> addMultipleStaff(List<StaffCreateRequest> requests, UUID staffId, UUID companyId, StaffRole role) {
        if (requests == null || requests.isEmpty()) {
            throw new ApiException("11", "Staff requests list cannot be null or empty", null);
        }

        List<String> failedAddDetails = new ArrayList<>();
        List<String> successDetails = new ArrayList<>();

        for (StaffCreateRequest request : requests) {
            try {
                addSingleStaff(request, staffId, companyId, role);
                successDetails.add(request.getStaffEmail());
            } catch (Exception e) {
                String errorMessage = String.format(
                        "Failed to add %s %s (%s): %s",
                        request.getStaffFirstName(),
                        request.getStaffLastName(),
                        request.getStaffEmail(),
                        e.getMessage() != null ? e.getMessage() : "Validation failed"
                );
                log.warn(errorMessage, e);
                failedAddDetails.add(errorMessage);
            }
        }

        if (failedAddDetails.isEmpty()) {
            return new BaseResponse<>("00", "All staff added successfully", successDetails);
        }

        return new BaseResponse<>(
                "22",
                String.format("%d staff added successfully, %d failed",
                        successDetails.size(), failedAddDetails.size()),
                Map.of(
                        "success", successDetails,
                        "failed", failedAddDetails
                )
        );
    }

    @Transactional
    public BaseResponse<?> activateStaffAccount(String staffEmail, String staffInviteToken) {
        if (staffEmail == null) {
            throw new ApiException("11", "Staff email is required", null);
        }
        if (staffInviteToken == null) {
            throw new ApiException("11", "Staff invite token is required", null);
        }

        Staff activatedStaff = staffRepository.getStaffByEmail(staffEmail);
        StaffValidator.validateStaff(activatedStaff);

        if (Boolean.TRUE.equals(activatedStaff.getStaffActivated()))
            return new BaseResponse<>("00", "Staff is already activated", null);


        List<StaffInvite> staffInvites = staffInviteRepository.getStaffInvitesByStaffEmail(staffEmail);
        if (staffInvites.isEmpty()) {
            throw new ApiException("44", "Staff has not been invited or needs to be reinvited", null);
        }

        StaffInvite matchedInvite = staffInvites.stream()
                .filter(inv -> staffInviteToken.equals(inv.getStaffInviteToken()))
                .findFirst()
                .orElseThrow(() -> new ApiException("55", "Invalid token", null));

        if (matchedInvite.getStaffInviteExpiresAt().isBefore(Instant.now())) {
            staffInviteRepository.deleteStaffInvite(matchedInvite.getStaffInviteId());
            throw new ApiException("55", "Token expired", null);
        }

        activatedStaff.setStaffActivated(true);
        activatedStaff.setStaffStatus(Status.ACTIVE);
        staffRepository.updateStaff(activatedStaff);

        mailService.sendActivationSuccessEmail(activatedStaff.getStaffEmail());

        // Clear all invites after activation
        staffInvites.forEach(invite ->
                staffInviteRepository.deleteStaffInvite(invite.getStaffInviteId())
        );

        return new BaseResponse<>("00", "Staff account activated successfully", null);
    }

    @Transactional
    public BaseResponse<?> requestStaffProfileUpdate(StaffUpdateRequest request, UUID staffId, UUID companyId, StaffRole role) {
        if (request == null) throw new ApiException("11", "Staff update request is null", null);

        Staff staff = staffRepository.getStaffById(request.getStaffUpdateStaffId());
        StaffValidator.validateStaff(staff);

        UUID staffDeptHeadId = staffRepository.getStaffDepartmentHeadId(staff.getStaffCompanyId(), staff.getStaffDepartment());
        Staff staffDeptHead = staffRepository.getStaffById(staffDeptHeadId);

        if (!staffId.equals(request.getStaffUpdateStaffId()) && !staffId.equals(staffDeptHeadId)) {
            throw new ApiException("44", "Staff id does not match / You are not allowed to update this profile", null);
        }

        StaffUpdate staffUpdate = staffUpdateRequestMapper.toEntity(request);
        staffUpdate.setStaffUpdateStaffDepartmentHeadId(staffDeptHeadId);
        staffUpdate.setStaffUpdateStaffStatus(Status.PENDING);
        staffUpdateRepository.createStaffUpdate(staffUpdate);

        mailService.sendStaffUpdateConfirmationEmail(staff.getStaffEmail());
        mailService.sendStaffUpdateApprovalRequestEmail(staffDeptHead.getStaffEmail());

        return new BaseResponse<>("00", "Staff profile update request has been sent", null);
    }

    @Transactional
    public BaseResponse<?> approveStaffProfileUpdate(UUID staffUpdateId, boolean approved, UUID staffId, UUID companyId, StaffRole role) {
        if (staffUpdateId == null) throw new ApiException("11", "Staff update id is required", null);

        StaffUpdate staffUpdate = staffUpdateRepository.getStaffUpdate(staffUpdateId);

        Staff staffDeptHead = staffRepository.getStaffById(staffUpdate.getStaffUpdateStaffDepartmentHeadId());
        StaffValidator.validateStaff(staffDeptHead);

        if (!staffDeptHead.getStaffId().equals(staffId))
            throw new ApiException("33", "You do not have permission to approve/reject this request", null);

        if (approved) {
            staffUpdate.setStaffUpdateStaffStatus(Status.APPROVED);
            staffUpdateRepository.updateStaffUpdateStatus(staffUpdate);

            Staff staffUpdated = staffRequestMapper.toEntity(staffUpdate);
            staffRepository.updateStaff(staffUpdated);
            staffUpdated = staffRepository.getStaffById(staffUpdate.getStaffUpdateStaffId());
            mailService.sendStaffUpdateApprovalEmail(staffUpdated.getStaffEmail());
            return new BaseResponse<>("00",  "Staff profile update request has been approved", null);
        } else {
            staffUpdate.setStaffUpdateStaffStatus(Status.REJECTED);
            staffUpdateRepository.updateStaffUpdateStatus(staffUpdate);

            Staff staffUpdated = staffRepository.getStaffById(staffUpdate.getStaffUpdateStaffId());
            mailService.sendStaffUpdateRejectionEmail(staffUpdated.getStaffEmail());
            return new BaseResponse<>("00",  "Staff profile update request has been rejected", null);
        }
    }

}
