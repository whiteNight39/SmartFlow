package com.whitenight.smartflow.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponse {

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

    private List<SupplierStaffResponse> supplierStaffList;

    private UUID supplierAddedBy;
}
