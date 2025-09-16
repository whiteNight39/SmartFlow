package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.StaffJwtAuth;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class StaffJwtAuthMapper implements RowMapper<StaffJwtAuth> {


    @Override
    public StaffJwtAuth mapRow(ResultSet rs, int rowNum) throws SQLException {
        return StaffJwtAuth.builder()
                .staffJwtAuthId(UUID.fromString(rs.getString("staff_jwt_auth_id")))
                .staffJwtAuthStaffId(UUID.fromString(rs.getString("staff_jwt_auth_staff_id")))
                .staffJwtAuthJwtToken(rs.getString("staff_jwt_auth_jwt_token"))
                .staffJwtAuthIssuedAt(rs.getDate("staff_jwt_auth_issued_at"))
                .staffJwtAuthExpiresAt(rs.getDate("staff_jwt_auth_expires_at"))
                .staffJwtAuthIsValid(rs.getBoolean("staff_jwt_auth_is_valid"))
                .staffJwtAuthUserDeviceIp(rs.getString("staff_jwt_auth_user_device_ip"))
                .staffJwtAuthUserDeviceAgent(rs.getString("staff_jwt_auth_user_device_agent"))
                .staffJwtAuthLevel(rs.getString("staff_jwt_auth_level"))
                .build();
    }

}
