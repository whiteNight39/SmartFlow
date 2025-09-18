package com.whitenight.smartflow.repository.database.query;

public class RFXQuery {

    public static final String CREATE_RFX = """
    INSERT INTO RFX (
        rfx_project_id,
        rfx_title,
        rfx_description,
        rfx_currency,
        rfx_status,
        rfx_issued_at,
        rfx_deadline,
        rfx_awarded_supplier_id,
        rfx_payment_split
    ) VALUES (
        :rfxProjectId,
        :rfxTitle,
        :rfxDescription,
        :rfxCurrency,
        :rfxStatus,
        :rfxIssuedAt,
        :rfxDeadline,
        :rfxAwardedSupplierId,
        :rfxPaymentSplit
    )
    RETURNING rfx_id;
""";

    public static final String GET_RFX_BY_ID = """
    SELECT
        r.rfx_project_id,
        r.rfx_title,
        r.rfx_description,
        r.rfx_currency,
        r.rfx_status,
        r.rfx_issued_at,
        r.rfx_deadline,
        r.rfx_awarded_supplier_id,
        r.rfx_payment_split,

        i.rfx_item_name,
        i.rfx_item_description,
        i.rfx_item_unit,
        i.rfx_item_quantity,
        i.rfx_item_unit_price_estimate,
        i.rfx_item_total_estimate

    FROM RFX r
    LEFT JOIN RFXItem i ON r.rfx_id = i.rfx_item_rfx_id
    WHERE r.rfx_id = :rfxId
""";


    public static final String UPDATE_RFX = """
    UPDATE RFX
    SET
        rfx_title = COALESCE(NULLIF(:rfxTitle, ''), rfx_title),
        rfx_description = COALESCE(NULLIF(:rfxDescription, ''), rfx_description),
        rfx_status = COALESCE(NULLIF(:rfxStatus, ''), rfx_status),
        rfx_issued_at = COALESCE(:rfxIssuedAt, rfx_issued_at)
    WHERE rfx_id = :rfxId;
""";

    public static final String DELETE_RFX = """
    UPDATE RFX
    SET rfx_status = 'DELETED'
    WHERE rfx_id = :rfxId;
""";

}
