package com.whitenight.smartflow.model.entity;

import com.whitenight.smartflow.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private UUID companyId;
    private String companyName;
    private String companyAddress;
    private String companyState;
    private String companyCountry;
    private String companyRegistrationNumber;
    private String companyContactFirstName;
    private String companyContactLastName;
    private String companyContactEmail;
    private String companyContactPhone;
    private String companyContactJobTitle;
    private String companyIndustry;

    private Status companyStatus;
    private Instant companyCreatedAt;
    private Instant companyUpdatedAt;
}
