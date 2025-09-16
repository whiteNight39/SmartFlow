package com.whitenight.smartflow.repository.database.implementation;

import com.whitenight.smartflow.model.entity.StaffInvite;
import com.whitenight.smartflow.repository.database.interfaces.StaffInviteRepository;
import com.whitenight.smartflow.repository.database.query.StaffInviteQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class StaffInviteRepositoryImpl implements StaffInviteRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StaffInviteRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void inviteStaff(StaffInvite staffInvite) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffInviteCSVUploadId", staffInvite.getStaffInviteCSVUploadId())
                .addValue("staffInviteEmail", staffInvite.getStaffInviteEmail())
                .addValue("staffInviteToken", staffInvite.getStaffInviteToken())
                .addValue("staffInviteExpiresAt", staffInvite.getStaffInviteExpiresAt());

        jdbcTemplate.update(StaffInviteQuery.INVITE_STAFF, params);
    }

    @Override
    public List<StaffInvite> getStaffInvitesByStaffEmail(String staffEmail) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffEmail", staffEmail);

        List<StaffInvite> staffInvites = jdbcTemplate.query(StaffInviteQuery.GET_STAFF_INVITE_BY_EMAIL, params,
                (rs, rowNum) -> StaffInvite.builder()
                        .staffInviteId(UUID.fromString(rs.getString("staff_invite_id")))
                        .staffInviteCSVUploadId(UUID.fromString(rs.getString("staff_invite_csv_upload_id")))
                        .staffInviteEmail(rs.getString("staff_invite_email"))
                        .staffInviteToken(rs.getString("staff_invite_token"))
                        .staffInviteExpiresAt(rs.getTimestamp("staff_invite_expires_at").toLocalDateTime())
                        .build());
        return staffInvites.isEmpty() ? null : staffInvites;
    }

    @Override
    public void deleteStaffInvite(UUID staffInviteId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffInviteId", staffInviteId);

        jdbcTemplate.update(StaffInviteQuery.DELETE_STAFF_INVITE, params);
    }
}
