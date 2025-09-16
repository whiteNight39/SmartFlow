package com.whitenight.smartflow.model.entity;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RFXItem {
    private UUID rfxItemId;
    private UUID rfxItemRFXId;                   // FK → RFX
    private String rfxItemName;
    private String rfxItemDescription;
    private String rfxItemUnit;              // e.g., KG, Litre, Piece
    private Integer rfxItemQuantity;
    private Double rfxItemUnitPriceEstimate; // Expected per-unit price
    private Double rfxItemTotalEstimate;     // (auto-calculated) = unit × qty
}