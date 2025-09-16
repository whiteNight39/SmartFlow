package com.whitenight.smartflow.repository.database.query;

public class CompanyQuery {

    public static final String ADD_COMPANY = """
    INSERT INTO Company (
        company_name,
        company_address,
        company_state,
        company_country,
        company_registration_number,
        company_contact_first_name,
        company_contact_last_name,
        company_contact_email,
        company_contact_phone,
        company_contact_job_title,
        company_industry,
        company_status,
        company_created_at,
        company_updated_at
    ) VALUES (
        :companyName,
        :companyAddress,
        :companyState,
        :companyCountry,
        :companyRegistrationNumber,
        :companyContactFirstName,
        :companyContactLastName,
        :companyContactEmail,
        :companyContactPhone,
        :companyContactJobTitle,
        :companyIndustry,
        'PENDING',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    )
""";

    public static final String GET_COMPANY_BY_ID = """
    SELECT
        company_id,
        company_name,
        company_address,
        company_state,
        company_country,
        company_registration_number,
        company_contact_first_name,
        company_contact_last_name,
        company_contact_email,
        company_contact_phone,
        company_contact_job_title,
        company_industry,
        company_status,
        company_created_at,
        company_updated_at
    FROM Company
    WHERE company_id = :companyId
      AND company_status != 'DELETED';
""";

    public static final String GET_COMPANY_BY_REGISTRATION_NUMBER = """
    SELECT
        company_id,
        company_name,
        company_address,
        company_state,
        company_country,
        company_registration_number,
        company_contact_first_name,
        company_contact_last_name,
        company_contact_email,
        company_contact_phone,
        company_contact_job_title,
        company_industry,
        company_status,
        company_created_at,
        company_updated_at
    FROM Company
    WHERE company_registration_number = :companyRegistrationNumber
      AND company_status != 'DELETED';
""";

    public static final String GET_ALL_COMPANIES = """
    SELECT
        company_id,
        company_name,
        company_address,
        company_state,
        company_country,
        company_registration_number,
        company_contact_first_name,
        company_contact_last_name,
        company_contact_email,
        company_contact_phone,
        company_contact_job_title,
        company_industry,
        company_status,
        company_created_at,
        company_updated_at
    FROM Company
    WHERE company_status != 'DELETED';
""";

    public static final String UPDATE_COMPANY = """
    UPDATE Company
    SET
        company_name = COALESCE(NULLIF(:companyName, ''), company_name),
        company_address = COALESCE(NULLIF(:companyAddress, ''), company_address),
        company_state = COALESCE(NULLIF(:companyState, ''), company_state),
        company_country = COALESCE(NULLIF(:companyCountry, ''), company_country),
        company_registration_number = COALESCE(NULLIF(:companyRegistrationNumber, ''), company_registration_number),
        company_contact_first_name = COALESCE(NULLIF(:companyContactFirstName, ''), company_contact_first_name),
        company_contact_last_name = COALESCE(NULLIF(:companyContactLastName, ''), company_contact_last_name),
        company_contact_email = COALESCE(NULLIF(:companyContactEmail, ''), company_contact_email),
        company_contact_phone = COALESCE(NULLIF(:companyContactPhone, ''), company_contact_phone),
        company_contact_job_title = COALESCE(NULLIF(:companyContactJobTitle, ''), company_contact_job_title),
        company_industry = COALESCE(NULLIF(:companyIndustry, ''), company_industry),
        company_status = COALESCE(NULLIF(:companyStatus, ''), company_status),
        company_updated_at = CURRENT_TIMESTAMP
    WHERE company_id = :companyId
      AND company_status != 'DELETED';
""";

    public static final String UNREGISTER_COMPANY = """
    WITH company_update AS (
        UPDATE Company
        SET company_status = 'DELETED',
            company_updated_at = CURRENT_TIMESTAMP
        WHERE company_id = :companyId
          AND company_status != 'DELETED'
    ),
    
    staff_update AS (
        UPDATE Staff
        SET staff_status = 'DELETED',
            staff_updated_at = CURRENT_TIMESTAMP
        WHERE staff_company_id IN (SELECT company_id FROM company_update)
          AND staff_status != 'DELETED'
    ),
    
    supplier_update AS (
        UPDATE Supplier
        SET supplier_status = 'DELETED',
            supplier_updated_at = CURRENT_TIMESTAMP
        WHERE supplier_company_id IN (SELECT company_id FROM company_update)
          AND supplier_status != 'DELETED'
    ),
    
    project_update AS (
        UPDATE Project
        SET project_status = 'DELETED',
            project_updated_at = CURRENT_TIMESTAMP
        WHERE project_company_id IN (SELECT company_id FROM company_update)
          AND project_status != 'DELETED'
    )
    
    UPDATE SupplierStaff
    SET supplier_staff_status = 'DELETED',
        supplier_staff_updated_at = CURRENT_TIMESTAMP
    WHERE supplier_staff_supplier_id IN (
        SELECT supplier_id FROM supplier_update
    )
    AND supplier_staff_status != 'DELETED';
""";

}
