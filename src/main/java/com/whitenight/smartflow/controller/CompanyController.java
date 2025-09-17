package com.whitenight.smartflow.controller;

import com.whitenight.smartflow.model.entity.Company;
import com.whitenight.smartflow.model.request.CompanyActivationRequest;
import com.whitenight.smartflow.model.request.CompanyCreateRequest;
import com.whitenight.smartflow.model.request.CompanyUpdateRequest;
import com.whitenight.smartflow.model.response.BaseResponse;
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
    public BaseResponse<?> onboardCompany(@Valid @RequestBody CompanyCreateRequest request) {

        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        UUID devId = userPrincipal.getUserId();

        return companyService.onboardCompany(request, devId);
    }

    @PatchMapping("/activate-company")
    public BaseResponse<?> activateCompany(@Valid @RequestBody CompanyActivationRequest request) {

        return companyService.activateCompany(request);
    }

    @GetMapping("/get-companies")
    public BaseResponse<?> getCompanies() {

        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        UUID devId = userPrincipal.getUserId();

        return companyService.getAllCompanies(devId);
    }

    @PatchMapping("/update-company-details")
    public BaseResponse<?> updateCompanyDetails(@Valid @RequestBody CompanyUpdateRequest request) {

        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        UUID staffId = userPrincipal.getUserId();
        String role = userPrincipal.getRole();
        UUID companyId = userPrincipal.getCompanyId();

        return companyService.updateCompanyDetails(request, staffId, companyId, role);
    }

    @PatchMapping("/update-company-contact")
    public BaseResponse<?> updateCompanyContactPerson(@Valid @RequestBody CompanyUpdateRequest request) {

        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        UUID staffId = userPrincipal.getUserId();
        String role = userPrincipal.getRole();
        UUID companyId = userPrincipal.getCompanyId();

        return companyService.updateCompanyContact(request, staffId, companyId, role);
    }

    @PatchMapping("/unregister-company")
    public BaseResponse<?> unregisterCompany(@Valid @RequestParam UUID companyId) {

        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        UUID devId = userPrincipal.getUserId();

        return companyService.unregisterCompany(companyId, devId);
    }


}
