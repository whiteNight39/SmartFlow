package com.whitenight.smartflow.repository.database.query;

public class StaffInviteQuery {

    public static final String INVITE_STAFF = """
    INSERT INTO SMARTFLOW_StaffInvite (
        staff_invite_csv_upload_id,
        staff_invite_email,
        staff_invite_token,
        staff_invite_expires_at,
        staff_invite_status,
        staff_invite_created_at,
        staff_invite_updated_at
    ) VALUES (
        :staffInviteCSVUploadId,
        :staffInviteEmail,
        :staffInviteToken,
        :staffInviteExpiresAt,
        'INVITED',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    )
""";

    public static final String GET_STAFF_INVITE_BY_EMAIL = """
            SELECT
                staff_invite_id,
                staff_invite_csv_upload_id,
                staff_invite_email,
                staff_invite_token,
                staff_invite_expires_at,
            FROM
                SMARTFLOW_StaffInvite
            WHERE   staff_invite_email = :staffInviteEmail
                AND staff_invite_staus = 'INVITED'
    """;

    public static final String DELETE_STAFF_INVITE = """
            UPDATE SMARTFLOW_StaffInvite
            SET
                staff_invite_status = 'DELETED/EXPIRED',
                staff_invite_updated_at = CURRENT_TIMESTAMP,
            WHERE
                staff_invite_id = :staffInviteId
    """;



}
