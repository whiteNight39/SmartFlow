package com.whitenight.smartflow.model.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreateRequest {

    @NotNull
    private UUID projectCompanyId;

    @NotBlank
    private String projectType; // Enum: RFX_PO, CONTRACT
    @NotNull
    private UUID projectCreatedById;        // FK → STAFF
    @NotNull
    private UUID projectSupplierId;         // FK → SUPPLIER

    private UUID projectAssignedSmId;       // FK → STAFF
    private UUID projectDeptHeadId;   // FK → STAFF

    @Future
    private LocalDateTime projectContractStartDate;
    @Future
    private LocalDateTime projectContractEndDate;
    private String projectContractCurrency;
    private Double projectContractTotalAmount;
    private String projectRfxCurrency;
    @Future
    private LocalDateTime projectRfxDeadline;
    private String projectRfxPaymentSplit;
}
