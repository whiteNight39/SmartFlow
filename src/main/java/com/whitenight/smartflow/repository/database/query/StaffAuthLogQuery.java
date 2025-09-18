package com.whitenight.smartflow.repository.database.query;

public class StaffAuthLogQuery {

    public static final String ADD_STAFF_AUTH_LOG = """
    INSERT INTO StaffAuthLog (
        staff_auth_log_staff_id,
        staff_auth_log_staff_level,
        staff_auth_log_last_authorization_date
    ) VALUES (
        :staffAuthLogStaffId,
        :staffAuthLogStaffLevel,
        CURRENT_TIMESTAMP
    )
    RETURNING staff_auth_log_id;
""";

    public static final String GET_STAFF_AUTH_LOG_BY_ID = """
    SELECT
        staff_auth_log_id,
        staff_auth_log_staff_id,
        staff_auth_log_staff_level,
        staff_auth_log_last_authorization_date
    FROM StaffAuthLog
    WHERE staff_auth_log_id = :staffAuthLogId;
""";

}
