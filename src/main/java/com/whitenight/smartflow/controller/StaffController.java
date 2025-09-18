package com.whitenight.smartflow.controller;

import com.whitenight.smartflow.model.entity.Staff;
import com.whitenight.smartflow.model.request.StaffCreateRequest;
import com.whitenight.smartflow.model.request.StaffSignInRequest;
import com.whitenight.smartflow.model.response.CompanyAPIResponse;
import com.whitenight.smartflow.model.response.StaffAPIResponse;
import com.whitenight.smartflow.model.response.StaffSignInResponse;
import com.whitenight.smartflow.repository.database.interfaces.StaffRepository;
import com.whitenight.smartflow.service.StaffService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;
    private final StaffRepository staffRepository;

    public StaffController(StaffService staffService, StaffRepository staffRepository) {
        this.staffService = staffService;
        this.staffRepository = staffRepository;
    }

    @PostMapping("/dev-signin")
    public ResponseEntity<StaffAPIResponse<Object>> devSignIn(@RequestBody StaffSignInRequest staffSignInRequest,
                                                              BindingResult bindingResult,
                                                              HttpServletRequest servletRequest) {

        String ip = servletRequest.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = servletRequest.getRemoteAddr();
        } else {
            ip = ip.split(",")[0];  // Use the first IP if multiple are present
        }
        String userAgent = servletRequest.getHeader("User-Agent");

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );

            StaffAPIResponse<Object> errorResponse = StaffAPIResponse.<Object>builder()
                    .staffAPIResponseCode("400")
                    .staffAPIResponseMessage("Validation failed")
                    .staffAPIResponseData(errors.toString())
                    .build();

            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {

            Staff staff = staffRepository.getStaffByEmail(staffSignInRequest.getStaffEmail());

            StaffSignInResponse signInResponse = staffService.devSignIn(staffSignInRequest.getStaffEmail(), staffSignInRequest.getStaffPassword(), ip, userAgent);

            StaffAPIResponse<Object> successResponse = StaffAPIResponse.<Object>builder()
                    .staffAPIResponseCode("200")
                    .staffAPIResponseMessage("Dev signed in")
                    .staffAPIResponseData(signInResponse)
                    .build();

            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {

            StaffAPIResponse<Object> errorResponse = StaffAPIResponse.<Object>builder()
                    .staffAPIResponseCode("500")
                    .staffAPIResponseMessage("ERROR")
                    .staffAPIResponseData("ERROR: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
