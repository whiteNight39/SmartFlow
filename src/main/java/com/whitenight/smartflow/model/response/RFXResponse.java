package com.whitenight.smartflow.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RFXResponse {

    private UUID rfxProjectId;               // FK → Project
    private String rfxTitle;
    private String rfxDescription;
    private String rfxCurrency;
    private String rfxStatus;             // e.g., OPEN, CLOSED, AWARDED
    private LocalDateTime rfxIssuedAt;
    private LocalDateTime rfxDeadline;
    private UUID rfxAwardedSupplierId;       // FK → Supplier
    private String rfxPaymentSplit;

    private List<RFXItemResponse> rfxItemList;
}
