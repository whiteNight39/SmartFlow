package com.whitenight.smartflow.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierCreateRequest {

    @NotNull
    private UUID supplierCompanyId;
    @NotBlank
    private String supplierName;
    @NotBlank
    @Email
    private String supplierEmail;
    private String supplierPhone;
    private String supplierAddress;
    private String supplierState;
    private String supplierCountry;
    @NotBlank
    private String supplierContactPersonFirstName;
    @NotBlank
    private String supplierContactPersonLastName;
    @NotBlank
    @Email
    private String supplierContactPersonEmail;
    @NotBlank
    private String supplierContactPersonPhone;

    @NotNull
    private UUID supplierAddedBy;
}
