package com.whitenight.smartflow.model.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    private UUID contractId;
    private UUID contractProjectId;                 // FK → Project
    private UUID contractSupplierId;                // FK → Supplier
    private String contractTitle;
    private String contractReferenceNumber;
    private String contractCurrency;
    private Double contractTotalAmount;
    private LocalDateTime contractStartDate;
    private LocalDateTime contractEndDate;
    private String contractStatus;          // ACTIVE, EXPIRED, TERMINATED
    private byte[] contractBriefFile;         // Optional: Contract brief PDF
//    private String contractTerms;           // Optional summary of key terms
}
