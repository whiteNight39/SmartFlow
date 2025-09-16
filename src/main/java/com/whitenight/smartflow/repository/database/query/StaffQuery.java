package com.whitenight.smartflow.repository.database.query;

public class StaffQuery {

    public static final String ADD_STAFF = """
    INSERT INTO SMARTFLOW_Staff (
        staff_first_name,
        staff_last_name,
        staff_email,
        staff_password,
        staff_phone,
        staff_role,
        staff_job_title,
        staff_smartflow_persona,
        staff_department,
        staff_who_added_id,
        staff_company_id,
        staff_department_head_id,
        
        staff_status,
        staff_activated,
        staff_created_at,
        staff_updated_at
    ) VALUES (
        :staffFirstName,
        :staffLastName,
        :staffEmail,
        :staffPassword,
        :staffPhone,
        :staffRole,
        :staffJobTitle,
        :staffSmartflowPersona,
        :staffDepartment,
        :staffWhoAddedId,
        :staffCompanyId,
        :staffDepartmentHeadId,
        
        'ACTIVE',
        FALSE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );
""";

    public static final String UPDATE_STAFF = """
    UPDATE SMARTFLOW_Staff
    SET
        staff_first_name = COALESCE(NULLIF(:staffFirstName, ''), staff_first_name),
        staff_last_name = COALESCE(NULLIF(:staffLastName, ''), staff_last_name),
        staff_email = COALESCE(NULLIF(:staffEmail, ''), staff_email),
        staff_phone = COALESCE(NULLIF(:staffPhone, ''), staff_phone),
        staff_role = COALESCE(NULLIF(:staffRole, ''), staff_role),
        staff_job_title = COALESCE(NULLIF(:staffJobTitle, ''), staff_job_title),
        staff_smartflow_persona = COALESCE(NULLIF(:staffSmartflowPersona, ''), staff_smartflow_persona),
        staff_department = COALESCE(NULLIF(:staffDepartment, ''), staff_department),
        staff_who_added_id = COALESCE(:staffWhoAddedId, staff_who_added_id),
        staff_company_id = COALESCE(:staffCompanyId, staff_company_id),
        staff_department_head_id = COALESCE(:staffDepartmentHeadId, staff_department_head_id),
        staff_auth_id = COALESCE(NULLIF(:staffAuthId, ''), staff_auth_id),
        staff_activated = COALESCE(NULLIF(:staffActivated, ''), staff_activated),
        staff_password = COALESCE(NULLIF(:staffPassword, ''), staff_password),
        staff_updated_at = CURRENT_TIMESTAMP
    WHERE staff_id = :staffId
      AND staff_status != 'INACTIVE';
""";

    public static final String GET_STAFF_BY_ID = """
    SELECT
        staff_id,
        staff_first_name,
        staff_last_name,
        staff_email,
        staff_phone,
        staff_role,
        staff_job_title,
        staff_smartflow_persona,
        staff_department,
        staff_who_added_id,
        staff_company_id,
        staff_department_head_id,
        staff_auth_id,
        staff_activated
    FROM SMARTFLOW_Staff
    WHERE staff_id = :staffId
      AND staff_status = 'ACTIVE';
""";

    public static final String GET_STAFF_BY_EMAIL = """
    SELECT
        staff_id,
        staff_first_name,
        staff_last_name,
        staff_email,
        staff_password,
        staff_phone,
        staff_role,
        staff_job_title,
        staff_smartflow_persona,
        staff_department,
        staff_who_added_id,
        staff_company_id,
        staff_department_head_id,
        staff_auth_id,
        staff_activated
    FROM SMARTFLOW_Staff
    WHERE staff_email = :staffEmail
      AND staff_status = 'ACTIVE';
""";

    public static final String GET_STAFF_DEPARTMENT_HEAD_ID = """
    SELECT staff_id
    FROM Smartflow_Staff
    WHERE staff_company_id = :staffCompanyId
      AND staff_department = :staffDepartment
      AND staff_role = 'DEPT_HEAD'
""";


    public static final String DELETE_STAFF = """
    UPDATE SMARTFLOW_Staff
    SET staff_status = 'INACTIVE',
        staff_updated_at = CURRENT_TIMESTAMP
    WHERE staff_id = :staffId
      AND staff_status != 'INACTIVE';
""";
}
