package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.StaffUpdate;
import com.whitenight.smartflow.model.enums.Status;
import com.whitenight.smartflow.utils.rank.StaffRole;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class StaffUpdateMapper implements RowMapper<StaffUpdate> {

    @Override
    public StaffUpdate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return StaffUpdate.builder()
                .staffUpdateId(getUuidOrNull(rs, "staff_update_id"))
                .staffUpdateStaffId(getUuidOrNull(rs, "staff_update_staff_id"))
                .staffUpdateStaffFirstName(rs.getString("staff_update_staff_first_name"))
                .staffUpdateStaffLastName(rs.getString("staff_update_staff_last_name"))
                .staffUpdateStaffEmail(rs.getString("staff_update_staff_email"))
                .staffUpdateStaffPhone(rs.getString("staff_update_staff_phone"))
                .staffUpdateStaffRole(StaffRole.valueOf(rs.getString("staff_update_staff_role")))
                .staffUpdateStaffJobTitle(rs.getString("staff_update_staff_job_title"))
                .staffUpdateStaffSmartflowPersona(rs.getString("staff_update_staff_smartflow_persona"))
                .staffUpdateStaffDepartment(rs.getString("staff_update_staff_department"))
                .staffUpdateStaffDepartmentHeadId(getUuidOrNull(rs, "staff_update_staff_department_head_id"))
                .staffUpdateStaffAuthId(rs.getString("staff_update_staff_auth_id"))
                .staffUpdateStaffPassword(rs.getString("staff_update_staff_password"))
                .staffUpdateStaffStatus(Status.valueOf(rs.getString("staff_update_staff_status")))
                .build();
    }

    private UUID getUuidOrNull(ResultSet rs, String columnLabel) throws SQLException {
        String value = rs.getString(columnLabel);
        return value == null ? null : UUID.fromString(value);
    }
}

