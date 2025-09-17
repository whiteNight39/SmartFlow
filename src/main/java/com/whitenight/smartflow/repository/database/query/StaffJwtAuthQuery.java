package com.whitenight.smartflow.repository.database.query;

public class StaffJwtAuthQuery {

    public static final String CREATE_STAFF_JWT_AUTH = """
    INSERT INTO SMARTFLOW_StaffJwtAuth (
        staff_jwt_auth_staff_id,
        staff_jwt_auth_jwt_token,
        staff_jwt_auth_issued_at,
        staff_jwt_auth_expires_at,
        staff_jwt_auth_user_device_ip,
        staff_jwt_auth_user_device_agent,
        staff_jwt_auth_level
    ) VALUES (
        :staffJwtAuthStaffId,
        :staffJwtAuthJwtToken,
        :staffJwtAuthIssuedAt,
        :staffJwtAuthExpiresAt,
        :staffJwtAuthUserDeviceIp,
        :staffJwtAuthUserDeviceAgent,
        :staffJwtAuthLevel
    )
""";

    public static final String GET_STAFF_JWT_AUTH = """
    SELECT
        staff_jwt_auth_id,
        staff_jwt_auth_staff_id,
        staff_jwt_auth_jwt_token,
        staff_jwt_auth_issued_at,
        staff_jwt_auth_expires_at,
        staff_jwt_auth_user_device_ip,
        staff_jwt_auth_user_device_agent,
        staff_jwt_auth_level
    FROM SMARTFLOW_StaffJwtAuth
    WHERE staff_jwt_auth_staff_id = :staffJwtAuthStaffId
      AND staff_jwt_auth_user_device_ip = :ip
      AND staff_jwt_auth_user_device_agent = :userAgent;
""";

}
