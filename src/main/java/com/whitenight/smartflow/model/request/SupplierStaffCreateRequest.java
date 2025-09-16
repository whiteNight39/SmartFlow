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
public class SupplierStaffCreateRequest {

    @NotNull
    private UUID supplierStaffSupplierId;

    @NotBlank
    private String supplierStaffFirstName;
    @NotBlank
    private String supplierStaffLastName;
    @NotBlank
    @Email
    private String supplierStaffEmail;
    @NotBlank
    private String supplierStaffPhone;
    @NotBlank
    private String supplierStaffRole;

//    @NotNull
    private UUID supplierStaffInvitedByStaffId;
    private UUID supplierStaffInvitedBySupplierStaffId;
}
