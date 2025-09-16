package com.whitenight.smartflow.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RFXItemCreateRequest {

    @NotNull
    private UUID rfxItemRFXId;                   // FK → RFX
    @NotBlank
    private String rfxItemName;
//    @NotBlank
    private String rfxItemDescription;
    @NotBlank
    private String rfxItemUnit;              // e.g., KG, Litre, Piece
    @NotNull
    private Integer rfxItemQuantity;
    @NotNull
    private Double rfxItemUnitPriceEstimate; // Expected per-unit price
}
