package com.whitenight.smartflow.repository.database.query;

public class RFXItemQuery {

    public static final String ADD_RFX_ITEM = """
    INSERT INTO SMARTFLOW_RFXItem (
        rfx_item_rfx_id,
        rfx_item_name,
        rfx_item_description,
        rfx_item_unit,
        rfx_item_quantity,
        rfx_item_unit_price_estimate,
        rfx_item_total_estimate
    ) VALUES (
        :rfxItemRfxId,
        :rfxItemName,
        :rfxItemDescription,
        :rfxItemUnit,
        :rfxItemQuantity,
        :rfxItemUnitPriceEstimate,
        :rfxItemTotalEstimate
    )
    RETURNING rfx_item_id;
""";

    public static final String GET_RFX_ITEM_BY_ID = """
    SELECT
        rfx_item_name,
        rfx_item_description,
        rfx_item_unit,
        rfx_item_quantity,
        rfx_item_unit_price_estimate,
        rfx_item_total_estimate
    FROM SMARTFLOW_RFXItem
    WHERE rfx_item_id = :rfxItemId;
""";

    public static final String UPDATE_RFX_ITEM = """
    UPDATE SMARTFLOW_RFXItem
    SET
        rfx_item_name = COALESCE(NULLIF(:rfxItemName, ''), rfx_item_name),
        rfx_item_description = COALESCE(NULLIF(:rfxItemDescription, ''), rfx_item_description),
        rfx_item_unit = COALESCE(NULLIF(:rfxItemUnit, ''), rfx_item_unit),
        rfx_item_quantity = COALESCE(:rfxItemQuantity, rfx_item_quantity),
        rfx_item_unit_price_estimate = COALESCE(:rfxItemUnitPriceEstimate, rfx_item_unit_price_estimate),
        rfx_item_total_estimate = COALESCE(:rfxItemTotalEstimate, rfx_item_total_estimate)
    WHERE rfx_item_id = :rfxItemId;
""";

    public static final String DELETE_RFX_ITEM = """
    DELETE FROM SMARTFLOW_RFXItem
    WHERE rfx_item_id = :rfxItemId;
""";

}
