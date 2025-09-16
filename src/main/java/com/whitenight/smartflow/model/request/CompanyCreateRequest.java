package com.whitenight.smartflow.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCreateRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;
    private String companyAddress;
    private String companyState;
    private String companyCountry;
    @NotBlank
    private String companyRegistrationNumber;
    @NotBlank
    private String companyContactFirstName;
    @NotBlank
    private String companyContactLastName;
    @NotBlank
    @Email
    private String companyContactEmail;
    @NotBlank
    private String companyContactPhone;
    private String companyContactJobTitle;
    private String companyIndustry;
}
