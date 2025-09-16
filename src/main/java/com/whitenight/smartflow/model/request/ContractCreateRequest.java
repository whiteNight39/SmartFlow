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
public class ContractCreateRequest {

    @NotNull
    private UUID contractProjectId;                 // FK → Project
    @NotNull
    private UUID contractSupplierId;                // FK → Supplier
    @NotBlank
    private String contractTitle;
    @NotBlank
    private String contractReferenceNumber;
    @NotBlank
    private String contractCurrency;
    @NotNull
    private Double contractTotalAmount;
    @NotNull
    private LocalDateTime contractStartDate;
    @NotNull
    @Future
    private LocalDateTime contractEndDate;
    private Byte[] contractBriefFile;         // Optional: link to PDF
}
