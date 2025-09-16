package com.whitenight.smartflow.repository.database.query;

public class SupplierQuery {

    public static final String ADD_SUPPLIER = """
    INSERT INTO SMARTFLOW_Supplier (
        supplier_company_id,
        supplier_name,
        supplier_email,
        supplier_phone,
        supplier_address,
        supplier_state,
        supplier_country,
        supplier_contact_person_first_name,
        supplier_contact_person_last_name,
        supplier_contact_person_email,
        supplier_contact_person_phone,
        supplier_added_by,
        supplier_status,
        supplier_created_at,
        supplier_updated_at
    ) VALUES (
        :supplierCompanyId,
        :supplierName,
        :supplierEmail,
        :supplierPhone,
        :supplierAddress,
        :supplierState,
        :supplierCountry,
        :supplierContactPersonFirstName,
        :supplierContactPersonLastName,
        :supplierContactPersonEmail,
        :supplierContactPersonPhone,
        :supplierAddedBy,
        'ACTIVE',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    )
    RETURNING supplier_id;
""";

            public static final String GET_SUPPLIER_BY_ID = """
            SELECT
                s.supplier_company_id,
                s.supplier_name,
                s.supplier_email,
                s.supplier_phone,
                s.supplier_address,
                s.supplier_state,
                s.supplier_country,
                s.supplier_contact_person_first_name,
                s.supplier_contact_person_last_name,
                s.supplier_contact_person_email,
                s.supplier_contact_person_phone,
                s.supplier_added_by,
        
                ss.supplier_staff_first_name,
                ss.supplier_staff_last_name,
                ss.supplier_staff_email,
                ss.supplier_staff_phone,
                ss.supplier_staff_role,
        
                -- whichever ID is present
                COALESCE(ss.supplier_staff_invited_by, ss.supplier_staff_invited_by_supplier_staff_id) AS supplier_staff_invited_by_id,
        
                -- label who invited them
                CASE
                    WHEN ss.supplier_staff_invited_by IS NOT NULL THEN 'CompanyStaff'
                    WHEN ss.supplier_staff_invited_by_supplier_staff_id IS NOT NULL THEN 'SupplierStaff'
                    ELSE NULL
                END AS supplier_staff_invited_by
                 
            FROM SMARTFLOW_Supplier s
            LEFT JOIN SMARTFLOW_SupplierStaff ss
                ON ss.supplier_staff_supplier_id = s.supplier_id
                AND ss.supplier_staff_status = 'ACTIVE'
            WHERE s.supplier_id = :supplierId
              AND s.supplier_status = 'ACTIVE';
        """;

    public static final String UPDATE_SUPPLIER = """
    UPDATE SMARTFLOW_Supplier
    SET
        supplier_name = COALESCE(NULLIF(:supplierName, ''), supplier_name),
        supplier_email = COALESCE(NULLIF(:supplierEmail, ''), supplier_email),
        supplier_phone = COALESCE(NULLIF(:supplierPhone, ''), supplier_phone),
        supplier_address = COALESCE(NULLIF(:supplierAddress, ''), supplier_address),
        supplier_state = COALESCE(NULLIF(:supplierState, ''), supplier_state),
        supplier_country = COALESCE(NULLIF(:supplierCountry, ''), supplier_country),
        supplier_contact_person_first_name = COALESCE(NULLIF(:supplierContactPersonFirstName, ''), supplier_contact_person_first_name),
        supplier_contact_person_last_name = COALESCE(NULLIF(:supplierContactPersonLastName, ''), supplier_contact_person_last_name),
        supplier_contact_person_email = COALESCE(NULLIF(:supplierContactPersonEmail, ''), supplier_contact_person_email),
        supplier_contact_person_phone = COALESCE(NULLIF(:supplierContactPersonPhone, ''), supplier_contact_person_phone),
        supplier_added_by = COALESCE(:supplierAddedBy, supplier_added_by),
        supplier_updated_at = CURRENT_TIMESTAMP
    WHERE supplier_id = :supplierId
      AND supplier_status = 'ACTIVE';
""";

    public static final String DELETE_SUPPLIER_BY_ID = """
    UPDATE SMARTFLOW_Supplier
    SET supplier_status = 'DELETED',
        supplier_updated_at = CURRENT_TIMESTAMP
    WHERE supplier_id = :supplierId
      AND supplier_status = 'ACTIVE';
""";


}
