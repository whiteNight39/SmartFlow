package com.whitenight.smartflow.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierStaffResponse {
    private String supplierStaffFirstName;
    private String supplierStaffLastName;
    private String supplierStaffEmail;
    private String supplierStaffPhone;
    private String supplierStaffRole;
    private UUID supplierStaffInvitedById;
    private String supplierStaffInvitedByLabel; // e.g., "CompanyStaff" or "SupplierStaff"
}

