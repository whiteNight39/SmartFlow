package com.whitenight.smartflow.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyActivationRequest {

    private String companyRegistrationNumber;
    private String companyContactEmail;
    private String token;
}
