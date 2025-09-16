package com.whitenight.smartflow.repository.database.query;

public class SupplierStaffQuery {

    public static final String ADD_SUPPLIER_STAFF = """
    INSERT INTO SMARTFLOW_SupplierStaff (
        supplier_staff_supplier_id,
        supplier_staff_first_name,
        supplier_staff_last_name,
        supplier_staff_email,
        supplier_staff_phone,
        supplier_staff_role,
        supplier_staff_invited_by_staff_id,
        supplier_staff_invited_by_supplier_staff_id,
        supplier_staff_status,
        supplier_staff_created_at,
        supplier_staff_updated_at
    ) VALUES (
        :supplierStaffSupplierId,
        :supplierStaffFirstName,
        :supplierStaffLastName,
        :supplierStaffEmail,
        :supplierStaffPhone,
        :supplierStaffRole,
        :supplierStaffInvitedByStaffId,
        :supplierStaffInvitedBySupplierStaffId,
        'PENDING',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    )
    RETURNING supplier_staff_id;
""";

    public static final String GET_SUPPLIER_STAFF_BY_ID = """
    SELECT
        supplier_staff_id,
        supplier_staff_supplier_id,
        supplier_staff_first_name,
        supplier_staff_last_name,
        supplier_staff_email,
        supplier_staff_phone,
        supplier_staff_role,
        supplier_staff_invited_by_staff_id,
        supplier_staff_invited_by_supplier_staff_id,
        supplier_staff_status,
        supplier_staff_created_at,
        supplier_staff_updated_at
    FROM SMARTFLOW_SupplierStaff
    WHERE supplier_staff_id = :supplierStaffId;
""";

    public static final String UPDATE_SUPPLIER_STAFF = """
    UPDATE SMARTFLOW_SupplierStaff
    SET
        supplier_staff_first_name = COALESCE(NULLIF(:supplierStaffFirstName, ''), supplier_staff_first_name),
        supplier_staff_last_name = COALESCE(NULLIF(:supplierStaffLastName, ''), supplier_staff_last_name),
        supplier_staff_email = COALESCE(NULLIF(:supplierStaffEmail, ''), supplier_staff_email),
        supplier_staff_phone = COALESCE(NULLIF(:supplierStaffPhone, ''), supplier_staff_phone),
        supplier_staff_role = COALESCE(NULLIF(:supplierStaffRole, ''), supplier_staff_role),
        supplier_staff_invited_by_staff_id = COALESCE(:supplierStaffInvitedByStaffId, supplier_staff_invited_by_staff_id),
        supplier_staff_invited_by_supplier_staff_id = COALESCE(:supplierStaffInvitedBySupplierStaffId, supplier_staff_invited_by_supplier_staff_id),
        supplier_staff_updated_at = CURRENT_TIMESTAMP
    WHERE supplier_staff_id = :supplierStaffId;
""";

    public static final String DELETE_SUPPLIER_STAFF = """
    UPDATE SMARTFLOW_SupplierStaff
    SET supplier_staff_status = 'DELETED',
        supplier_staff_updated_at = CURRENT_TIMESTAMP
    WHERE supplier_staff_id = :supplierStaffId;
""";
}
