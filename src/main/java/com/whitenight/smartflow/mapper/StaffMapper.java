package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.Staff;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class StaffMapper implements RowMapper<Staff> {

    @Override
    public Staff mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Staff.builder()
                .staffId(getUuidOrNull(rs, "staff_id"))
                .staffFirstName(rs.getString("staff_first_name"))
                .staffLastName(rs.getString("staff_last_name"))
                .staffEmail(rs.getString("staff_email"))
                .staffPhone(rs.getString("staff_phone"))
                .staffRole(rs.getString("staff_role"))
                .staffJobTitle(rs.getString("staff_job_title"))
                .staffSmartflowPersona(rs.getString("staff_smartflow_persona"))
                .staffWhoAddedId(getUuidOrNull(rs, "staff_who_added_id"))
                .staffCompanyId(getUuidOrNull(rs, "staff_company_id"))
                .staffDepartmentHeadId(getUuidOrNull(rs, "staff_department_head_id"))
                .staffAuthId(rs.getString("staff_auth_id"))
                .staffActivated(rs.getBoolean("staff_activated"))
                .build();
    }

    private UUID getUuidOrNull(ResultSet rs, String columnLabel) throws SQLException {
        String value = rs.getString(columnLabel);
        return value == null ? null : UUID.fromString(value);
    }
}
