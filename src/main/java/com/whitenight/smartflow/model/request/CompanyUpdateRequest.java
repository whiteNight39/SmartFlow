package com.whitenight.smartflow.model.request;

import com.whitenight.smartflow.model.enums.Status;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateRequest {

    private String companyName;
    private String companyAddress;
    private String companyState;
    private String companyCountry;
    private String companyRegistrationNumber;
    private String companyIndustry;

    private String companyContactFirstName;
    private String companyContactLastName;
    @Email
    private String companyContactEmail;
    private String companyContactPhone;
    private String companyContactJobTitle;

    private Status companyStatus;

}
