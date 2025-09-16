package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.StaffAuthLog;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class StaffAuthLogMapper implements RowMapper<StaffAuthLog> {

    @Override
    public StaffAuthLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        return StaffAuthLog.builder()
                .staffAuthLogId(UUID.fromString(rs.getString("staff_auth_log_id")))
                .staffAuthLogStaffId(UUID.fromString(rs.getString("staff_auth_log_staff_id")))
                .staffAuthLogStaffLevel(rs.getString("staff_auth_log_staff_level"))
                .staffAuthLogLastAuthorizationDate(rs.getTimestamp("staff_auth_log_last_authorization_date").toLocalDateTime())
                .build();
    }
}
