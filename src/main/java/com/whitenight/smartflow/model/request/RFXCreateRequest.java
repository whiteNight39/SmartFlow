package com.whitenight.smartflow.model.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class RFXCreateRequest {

    @NotBlank
    private String rfxTitle;
    @NotBlank
    private String rfxDescription;

    @NotNull
    private UUID rfxProjectId;               // FK → Project
    @NotBlank
    private String rfxCurrency;
    @NotBlank
    private String rfxStatus;             // e.g., OPEN, CLOSED, AWARDED
    @Future
    @NotNull
    private LocalDateTime rfxDeadline;
    @NotNull
    private UUID rfxAwardedSupplierId;       // FK → Supplier
    @NotBlank
    private String rfxPaymentSplit;
}
