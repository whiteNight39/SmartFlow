package com.whitenight.smartflow.service;

import com.whitenight.smartflow.mapper.StaffInviteRequestMapper;
import com.whitenight.smartflow.mapper.StaffJwtAuthMapper;
import com.whitenight.smartflow.mapper.StaffRequestMapper;
import com.whitenight.smartflow.model.entity.Staff;
import com.whitenight.smartflow.model.entity.StaffInvite;
import com.whitenight.smartflow.model.entity.StaffJwtAuth;
import com.whitenight.smartflow.model.request.StaffCreateRequest;
import com.whitenight.smartflow.model.request.StaffInviteCreateRequest;
import com.whitenight.smartflow.model.response.StaffAccountDetails;
import com.whitenight.smartflow.model.response.StaffSignInResponse;
import com.whitenight.smartflow.repository.database.interfaces.StaffInviteRepository;
import com.whitenight.smartflow.repository.database.interfaces.StaffJwtAuthRepository;
import com.whitenight.smartflow.repository.database.interfaces.StaffRepository;
import com.whitenight.smartflow.utils.TokenGenerator;
import com.whitenight.smartflow.utils.jwt.JwtUtil;
import com.whitenight.smartflow.utils.rank.RoleUtils;
import com.whitenight.smartflow.utils.rank.StaffRole;
import com.whitenight.smartflow.utils.validator.StaffValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final StaffRequestMapper staffRequestMapper;

    private final StaffInviteRepository staffInviteRepository;
    private final StaffInviteRequestMapper staffInviteRequestMapper;

    private final StaffJwtAuthRepository staffJwtAuthRepository;
    private final StaffJwtAuthMapper staffJwtAuthMapper;

    private final JwtUtil jwtUtil;

    private final MailService mailService;


    public StaffService(StaffRepository staffRepository, StaffJwtAuthRepository staffJwtAuthRepository, StaffJwtAuthMapper staffJwtAuthMapper, JwtUtil jwtUtil, StaffRequestMapper staffRequestMapper, StaffInviteRepository staffInviteRepository, StaffInviteRequestMapper staffInviteRequestMapper, MailService mailService) {
        this.staffRepository = staffRepository;
        this.staffJwtAuthRepository = staffJwtAuthRepository;
        this.staffJwtAuthMapper = staffJwtAuthMapper;
        this.jwtUtil = jwtUtil;
        this.staffRequestMapper = staffRequestMapper;
        this.staffInviteRepository = staffInviteRepository;
        this.staffInviteRequestMapper = staffInviteRequestMapper;
        this.mailService = mailService;
    }

    public StaffSignInResponse devSignIn(String devEmail, String devPassword, String ip, String userAgent) {
        if (devEmail == null) throw new IllegalArgumentException("devAuthId cannot be null");
        if (devPassword == null) throw new IllegalArgumentException("devPassword cannot be null");

        StaffAccountDetails dev = staffRepository.getStaffByEmail(devEmail);
        if (dev == null) throw new IllegalArgumentException("Invalid email or password");
        StaffValidator.validateRoles(dev, "DEVELOPER");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(devPassword, dev.getStaffPassword())) {

            StaffJwtAuth auth = staffJwtAuthRepository.getStaffJwtAuth(dev.getStaffId(), ip, userAgent);
            String token;

            if (auth == null) {

                token = jwtUtil.generateToken(dev.getStaffId());

                StaffJwtAuth staffJwtAuth = StaffJwtAuth.builder()
                        .staffJwtAuthStaffId(jwtUtil.extractUserId(token))
                        .staffJwtAuthJwtToken(token)
                        .staffJwtAuthIssuedAt(jwtUtil.getIssueDate(token))
                        .staffJwtAuthExpiresAt(jwtUtil.getExpirationDate(token))
                        .staffJwtAuthIsValid(jwtUtil.isTokenValid(token))
                        .staffJwtAuthUserDeviceIp(ip)
                        .staffJwtAuthUserDeviceAgent(userAgent)
                        .staffJwtAuthLevel(dev.getStaffRole())
                        .build();

                staffJwtAuthRepository.createStaffJwtAuth(staffJwtAuth);
            } else {
                token = auth.getStaffJwtAuthJwtToken();
            }

            return StaffSignInResponse.builder()
                    .staffJwtToken(token)
                    .build();
        }
        throw new IllegalArgumentException("Invalid email or password");
    }

    public void addSingleStaff(StaffCreateRequest request, UUID staffId, UUID companyId, String role) {
        if (request == null) throw new IllegalArgumentException("Staff request cannot be null");
        if (staffId == null) throw new IllegalArgumentException("Staff ID is required");

        Staff staffAdding = staffRepository.getStaffById(staffId);
        StaffValidator.validateStaff(staffAdding);

        Staff staffAdded = staffRequestMapper.toEntity(request);
        staffAdded.setStaffWhoAddedId(staffId);
        staffAdded.setStaffCompanyId(companyId);
        staffAdded.setStaffDepartmentHeadId(staffRepository.getStaffDepartmentHeadId(companyId, request.getStaffDepartment()));
        String tempPassword = TokenGenerator.generateSecureToken(12); // or any random string
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(tempPassword); // Use BCrypt or whatever you're using
        staffAdded.setStaffPassword(hashedPassword);

        StaffRole addingRole, addedRole;
        try {
            addingRole = StaffRole.valueOf(role.toUpperCase());
            addedRole = StaffRole.valueOf(request.getStaffRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role provided");
        }

        if (!RoleUtils.canAdd(addingRole, addedRole)) {
            throw new AccessDeniedException("You are not authorized to add a staff with this role");
        }

        StaffInviteCreateRequest inviteCreateRequest = StaffInviteCreateRequest.builder()
                .staffInviteCSVUploadId(null)
                .staffInviteEmail(request.getStaffEmail())
                .staffInviteToken(TokenGenerator.generateSecureToken(16))
                .staffInviteExpiresAt(LocalDateTime.now().plusDays(7))
                .build();
        StaffInvite invite = staffInviteRequestMapper.toEntity(inviteCreateRequest);
        staffInviteRepository.inviteStaff(invite);

        mailService.sendActivationEmail(invite.getStaffInviteEmail(), invite.getStaffInviteToken());

        staffRepository.addStaff(staffAdded);
    }

    private static final Logger log = LoggerFactory.getLogger(StaffService.class);

    public List<String> addMultipleStaff(List<StaffCreateRequest> requests, UUID staffId, UUID companyId, String role) {

        List<String> failedAddDetails = new ArrayList<>();

        for (StaffCreateRequest request : requests) {
            try {
                addSingleStaff(request, staffId, companyId, role);
            } catch (Exception e) {
                String errorMessage = String.format(
                        "Failed to add %s %s (%s): %s",
                        request.getStaffFirstName(),
                        request.getStaffLastName(),
                        request.getStaffEmail(),
                        e.getMessage()
                );

                log.warn(errorMessage, e);  // Logs full stack trace as well
                failedAddDetails.add(errorMessage);
            }
        }

        return failedAddDetails;
    }

    public void activateStaffAccount(String staffEmail, String staffInviteToken) {
        if (staffEmail == null) throw new IllegalArgumentException("Staff email is required");
        if (staffInviteToken == null) throw new IllegalArgumentException("Staff invite token is required");

        List<StaffInvite> staffInvites = staffInviteRepository.getStaffInvitesByStaffEmail(staffEmail);

        boolean foundMatchingToken = false;
        boolean activated = false;

        for (StaffInvite invite : staffInvites) {
            if (invite.getStaffInviteToken().equals(staffInviteToken)) {
                foundMatchingToken = true;

                if (invite.getStaffInviteExpiresAt().isBefore(LocalDateTime.now())) {
                    staffInviteRepository.deleteStaffInvite(invite.getStaffInviteId()); // expired → delete
                    continue;
                }

                // Valid token → activate
                StaffAccountDetails staffAccountDetails = staffRepository.getStaffByEmail(staffEmail);
                Staff staff = Staff.builder()
                        .staffId(staffAccountDetails.getStaffId())
                        .staffActivated(true)
                        .build();
                staffRepository.updateStaff(staff);
                activated = true;

                staffInviteRepository.deleteStaffInvite(invite.getStaffInviteId()); // success → delete
                break;
            }
        }

        if (!foundMatchingToken) {
            throw new IllegalStateException("Invalid token");
        }

        if (!activated) {
            throw new IllegalStateException("Token is expired");
        }
    }




}
