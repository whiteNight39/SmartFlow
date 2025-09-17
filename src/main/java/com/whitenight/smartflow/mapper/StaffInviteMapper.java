package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.StaffInvite;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class StaffInviteMapper implements RowMapper<StaffInvite> {

    @Override
    public StaffInvite mapRow(ResultSet rs, int rowNum) throws SQLException {
        return StaffInvite.builder()
                .staffInviteId(getUuidOrNull(rs, "staff_invite_id"))
                .staffInviteEmail(rs.getString("staff_invite_email"))
                .staffInviteToken(rs.getString("staff_invite_token"))
                .staffInviteCSVUploadId(getUuidOrNull(rs, "staff_invite_csv_upload_id"))
                .staffInviteExpiresAt(
                        rs.getTimestamp("staff_invite_expires_at") != null
                                ? rs.getTimestamp("staff_invite_expires_at").toInstant()
                                : null
                )
                .build();
    }

    private UUID getUuidOrNull(ResultSet rs, String columnLabel) throws SQLException {
        String value = rs.getString(columnLabel);
        return value == null ? null : UUID.fromString(value);
    }
}
