package com.whitenight.smartflow.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RFXItemResponse {

    private String rfxItemName;
    private String rfxItemDescription;
    private String rfxItemUnit;              // e.g., KG, Litre, Piece
    private Integer rfxItemQuantity;
    private Double rfxItemUnitPriceEstimate; // Expected per-unit price
    private Double rfxItemTotalEstimate;     // (auto-calculated) = unit × qty
}
