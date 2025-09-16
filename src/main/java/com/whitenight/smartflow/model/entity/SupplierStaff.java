package com.whitenight.smartflow.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierStaff {

    private UUID supplierStaffId;
    private UUID supplierStaffSupplierId;

    private String supplierStaffFirstName;
    private String supplierStaffLastName;
    private String supplierStaffEmail;
    private String supplierStaffPhone;
    private String supplierStaffRole;
    private UUID supplierStaffInvitedByStaffId;
    private UUID supplierStaffInvitedBySupplierStaffId;

    private String supplierStaffStatus; // e.g. PENDING, ACTIVE
    private LocalDateTime supplierStaffCreatedAt;
    private LocalDateTime supplierStaffUpdatedAt;
}
