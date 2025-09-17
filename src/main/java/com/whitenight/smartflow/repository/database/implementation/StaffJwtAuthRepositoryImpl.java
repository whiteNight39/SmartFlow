package com.whitenight.smartflow.repository.database.implementation;

import com.whitenight.smartflow.mapper.StaffJwtAuthMapper;
import com.whitenight.smartflow.model.entity.StaffJwtAuth;
import com.whitenight.smartflow.repository.database.interfaces.StaffJwtAuthRepository;
import com.whitenight.smartflow.repository.database.query.StaffJwtAuthQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class StaffJwtAuthRepositoryImpl implements StaffJwtAuthRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StaffJwtAuthRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createStaffJwtAuth(StaffJwtAuth staffJwtAuth) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffJwtAuthStaffId", staffJwtAuth.getStaffJwtAuthStaffId())
                .addValue("staffJwtAuthJwtToken", staffJwtAuth.getStaffJwtAuthJwtToken())
                .addValue("staffJwtAuthIssuedAt", staffJwtAuth.getStaffJwtAuthIssuedAt())
                .addValue("staffJwtAuthExpiresAt", staffJwtAuth.getStaffJwtAuthExpiresAt())
                .addValue("staffJwtAuthUserDeviceIp", staffJwtAuth.getStaffJwtAuthUserDeviceIp())
                .addValue("staffJwtAuthUserDeviceAgent", staffJwtAuth.getStaffJwtAuthUserDeviceAgent())
                .addValue("staffJwtAuthLevel", staffJwtAuth.getStaffJwtAuthLevel());

        jdbcTemplate.update(StaffJwtAuthQuery.CREATE_STAFF_JWT_AUTH, params);
    }

    @Override
    public StaffJwtAuth getStaffJwtAuth(UUID staffJwtAuthStaffId, String ip, String userAgent) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffJwtAuthStaffId", staffJwtAuthStaffId)
                .addValue("ip", ip)
                .addValue("userAgent", userAgent);

        List<StaffJwtAuth> auths = jdbcTemplate.query(StaffJwtAuthQuery.GET_STAFF_JWT_AUTH, params,
                new StaffJwtAuthMapper());

        return auths.isEmpty() ? null : auths.getFirst();
    }
}