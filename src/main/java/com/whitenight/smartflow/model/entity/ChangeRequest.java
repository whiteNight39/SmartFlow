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
public class ChangeRequest {

    private UUID changeRequestId;
    private UUID changeRequestRequestedByStaffId;
    private UUID changeRequestRequestedBySupplierStaffId;
    private UUID changeRequestHandledBy;
    private UUID changeRequestSupplierId;
    private String[] changeRequestOldData; // JSON string
    private String[] changeRequestNewData; // JSON string
    private String changeRequestReason;
    private Boolean changeRequestSmApproved;
    private String changeRequestSmApprovalNotes;
    private String changeRequestStatus; // PENDING, APPROVED, REJECTED
    private LocalDateTime changeRequestCreatedAt;
    private LocalDateTime changeRequestResolvedAt;
}
