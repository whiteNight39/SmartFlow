package com.whitenight.smartflow.repository.database.query;

public class StaffUpdateQuery {

    public static final String CREATE_STAFF_UPDATE = """
    INSERT INTO StaffUpdate (
        staff_update_staff_id,
        staff_update_staff_first_name,
        staff_update_staff_last_name,
        staff_update_staff_email,
        staff_update_staff_phone,
        staff_update_staff_role,
        staff_update_staff_job_title,
        staff_update_staff_smartflow_persona,
        staff_update_staff_department,
        staff_update_staff_department_head_id,
        staff_update_staff_password,
        staff_update_staff_status
    ) VALUES (
        COALESCE(:staffUpdateStaffId, NULL),
        COALESCE(:staffUpdateStaffFirstName, NULL),
        COALESCE(:staffUpdateStaffLastName, NULL),
        COALESCE(:staffUpdateStaffEmail, NULL),
        COALESCE(:staffUpdateStaffPhone, NULL),
        COALESCE(:staffUpdateStaffRole, NULL),
        COALESCE(:staffUpdateStaffJobTitle, NULL),
        COALESCE(:staffUpdateStaffSmartflowPersona, NULL),
        COALESCE(:staffUpdateStaffDepartment, NULL),
        COALESCE(:staffUpdateStaffDepartmentHeadId, NULL),
        COALESCE(:staffUpdateStaffPassword, NULL),
        COALESCE(:staffUpdateStaffStatus, 'PENDING')
    );
""";

    public static final String GET_STAFF_UPDATE = """
    SELECT
        staff_update_id,
        staff_update_staff_id,
        staff_update_staff_first_name,
        staff_update_staff_last_name,
        staff_update_staff_email,
        staff_update_staff_phone,
        staff_update_staff_role,
        staff_update_staff_job_title,
        staff_update_staff_smartflow_persona,
        staff_update_staff_department,
        staff_update_staff_department_head_id,
        staff_update_staff_auth_id,
        staff_update_staff_password,
        staff_update_staff_status
    FROM StaffUpdate
    WHERE staff_update_id = :staffUpdateId;
""";

    public static final String UPDATE_STAFF_UPDATE_STATUS = """
    UPDATE StaffUpdate
    SET
        staff_update_staff_status = :staffUpdateStaffStatus
    WHERE staff_update_id = :staffUpdateId;
""";


}
