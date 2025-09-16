package com.whitenight.smartflow.controller;

import com.whitenight.smartflow.model.entity.Company;
import com.whitenight.smartflow.model.request.CompanyCreateRequest;
import com.whitenight.smartflow.model.request.CompanyUpdateRequest;
import com.whitenight.smartflow.model.response.CompanyAPIResponse;
import com.whitenight.smartflow.service.CompanyService;
import com.whitenight.smartflow.utils.jwt.CustomUserPrincipal;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/onboard-company")
    public ResponseEntity<CompanyAPIResponse<String>> onboardCompany(@Valid @RequestBody CompanyCreateRequest request,
                                                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );

            CompanyAPIResponse<String> errorResponse = CompanyAPIResponse.<String>builder()
                    .companyAPIResponseCode("400")
                    .companyAPIResponseMessage("Validation failed")
                    .companyAPIResponseData(errors.toString())
                    .build();

            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            CustomUserPrincipal userPrincipal = (CustomUserPrincipal) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            UUID devId = userPrincipal.getUserId();
            String role = userPrincipal.getRole();
            UUID companyId = userPrincipal.getCompanyId();

            companyService.onboardCompany(request, devId);

            CompanyAPIResponse<String> successResponse = CompanyAPIResponse.<String>builder()
                    .companyAPIResponseCode("200")
                    .companyAPIResponseMessage("Company Onboarded Successfully")
                    .build();

            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {

            CompanyAPIResponse<String> errorResponse = CompanyAPIResponse.<String>builder()
                    .companyAPIResponseCode("500")
                    .companyAPIResponseMessage("ERROR")
                    .companyAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/get-companies")
    public ResponseEntity<CompanyAPIResponse<Object>> getCompanies() {

        try {
            CustomUserPrincipal userPrincipal = (CustomUserPrincipal) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            UUID devId = userPrincipal.getUserId();
            String role = userPrincipal.getRole();
            UUID devCompanyId = userPrincipal.getCompanyId();

            List<Company> companies = companyService.getAllCompanies(devId);

            CompanyAPIResponse<Object> successResponse = CompanyAPIResponse.<Object>builder()
                    .companyAPIResponseCode("200")
                    .companyAPIResponseMessage("Companies Retieved")
                    .companyAPIResponseData(companies)
                    .build();

            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {

            CompanyAPIResponse<Object> errorResponse = CompanyAPIResponse.<Object>builder()
                    .companyAPIResponseCode("500")
                    .companyAPIResponseMessage("ERROR")
                    .companyAPIResponseData("ERROR: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PatchMapping("/update-company-details")
    public ResponseEntity<CompanyAPIResponse<String>> updateCompanyDetails(@Valid @RequestBody CompanyUpdateRequest request) {

        try {
            CustomUserPrincipal userPrincipal = (CustomUserPrincipal) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            UUID staffId = userPrincipal.getUserId();
            String role = userPrincipal.getRole();
            UUID companyId = userPrincipal.getCompanyId();

            companyService.updateCompanyDetails(request, staffId, companyId, role);

            CompanyAPIResponse<String> successResponse = CompanyAPIResponse.<String>builder()
                    .companyAPIResponseCode("200")
                    .companyAPIResponseMessage("Company Updated Successfully")
                    .build();

            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {

            CompanyAPIResponse<String> errorResponse = CompanyAPIResponse.<String>builder()
                    .companyAPIResponseCode("500")
                    .companyAPIResponseMessage("ERROR")
                    .companyAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PatchMapping("/update-company-contact")
    public ResponseEntity<CompanyAPIResponse<String>> updateCompanyContactPerson(@Valid @RequestBody CompanyContactUpdateRequest request) {

        try {
            CustomUserPrincipal userPrincipal = (CustomUserPrincipal) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            UUID staffId = userPrincipal.getUserId();
            String role = userPrincipal.getRole();
            UUID companyId = userPrincipal.getCompanyId();

            companyService.updateCompanyContactPerson(request, staffId, companyId, role);

            CompanyAPIResponse<String> successResponse = CompanyAPIResponse.<String>builder()
                    .companyAPIResponseCode("200")
                    .companyAPIResponseMessage("Contact Person Updated Successfully")
                    .build();

            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {

            CompanyAPIResponse<String> errorResponse = CompanyAPIResponse.<String>builder()
                    .companyAPIResponseCode("500")
                    .companyAPIResponseMessage("ERROR")
                    .companyAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PatchMapping("/unregister-company")
    public ResponseEntity<CompanyAPIResponse<String>> unregisterCompany(@Valid @RequestParam UUID companyId) {

        try {
            CustomUserPrincipal userPrincipal = (CustomUserPrincipal) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            UUID devId = userPrincipal.getUserId();
            String role = userPrincipal.getRole();
            UUID devCompanyId = userPrincipal.getCompanyId();

            companyService.unregisterCompany(companyId, devId);

            CompanyAPIResponse<String> successResponse = CompanyAPIResponse.<String>builder()
                    .companyAPIResponseCode("200")
                    .companyAPIResponseMessage("Contact Unregistered")
                    .build();

            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {

            CompanyAPIResponse<String> errorResponse = CompanyAPIResponse.<String>builder()
                    .companyAPIResponseCode("500")
                    .companyAPIResponseMessage("ERROR")
                    .companyAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


}
