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
public class Project {

    private UUID projectId;
    private UUID projectCompanyId;

    private String projectType; // Enum: RFX_PO, CONTRACT
    private UUID projectCreatedById;        // FK → STAFF
    private UUID projectSupplierId;         // FK → SUPPLIER

    private UUID projectAssignedSmId;       // FK → STAFF
    private boolean projectSmApproved;
    private String projectSmApprovalNotes;

    private UUID projectDeptHeadId;   // FK → STAFF
//    private UUID projectAuthorizedId;       // Required DeptHead ID (provided at creation)
    private boolean projectFinalAuthorised;
    private String projectFinalApprovalNotes;

    private LocalDateTime projectContractStartDate;
    private LocalDateTime projectContractEndDate;
    private String projectContractCurrency;
    private Double projectContractTotalAmount;
    private String projectRfxCurrency;
    private LocalDateTime projectRfxDeadline;
    private String projectRfxPaymentSplit;

    private String projectStatus;    // Enum: PENDING, SM_APPROVED, FINAL_AUTHORISED, REJECTED
    private LocalDateTime projectCreatedAt;
    private LocalDateTime projectUpdatedAt;
}
