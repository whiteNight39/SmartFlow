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
public class Supplier {

    private UUID supplierId;
    private UUID supplierCompanyId;

    private String supplierName;
    private String supplierEmail;
    private String supplierPhone;
    private String supplierAddress;
    private String supplierState;
    private String supplierCountry;

    private String supplierContactPersonFirstName;
    private String supplierContactPersonLastName;
    private String supplierContactPersonEmail;
    private String supplierContactPersonPhone;

    private UUID supplierAddedBy;

    private String supplierStatus;
    private LocalDateTime supplierCreatedAt;
    private LocalDateTime supplierUpdatedAt;
}
