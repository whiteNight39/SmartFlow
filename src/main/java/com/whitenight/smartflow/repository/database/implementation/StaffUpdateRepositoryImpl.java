package com.whitenight.smartflow.repository.database.implementation;

import com.whitenight.smartflow.mapper.StaffUpdateMapper;
import com.whitenight.smartflow.model.entity.StaffUpdate;
import com.whitenight.smartflow.repository.database.interfaces.StaffUpdateRepository;
import com.whitenight.smartflow.repository.database.query.StaffUpdateQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class StaffUpdateRepositoryImpl implements StaffUpdateRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StaffUpdateRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createStaffUpdate(StaffUpdate staffUpdate) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffUpdateStaffFirstName", staffUpdate.getStaffUpdateStaffFirstName())
                .addValue("staffUpdateStaffLastName", staffUpdate.getStaffUpdateStaffLastName())
                .addValue("staffUpdateStaffEmail", staffUpdate.getStaffUpdateStaffEmail())
                .addValue("staffUpdateStaffPassword", staffUpdate.getStaffUpdateStaffPassword())
                .addValue("staffUpdateStaffPhone", staffUpdate.getStaffUpdateStaffPhone())
                .addValue("staffUpdateStaffRole", staffUpdate.getStaffUpdateStaffRole())
                .addValue("staffUpdateStaffJobTitle", staffUpdate.getStaffUpdateStaffJobTitle())
                .addValue("staffUpdateStaffSmartflowPersona",  staffUpdate.getStaffUpdateStaffSmartflowPersona())
                .addValue("staffUpdateStaffDepartment", staffUpdate.getStaffUpdateStaffDepartment())
                .addValue("staffUpdateStaffDepartmentHeadId", staffUpdate.getStaffUpdateStaffDepartmentHeadId())
                .addValue("staffUpdateStaffStatus", staffUpdate.getStaffUpdateStaffStatus());

        jdbcTemplate.update(StaffUpdateQuery.CREATE_STAFF_UPDATE, params);
    }

    @Override
    public StaffUpdate getStaffUpdate(UUID staffUpdateId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffUpdateId", staffUpdateId);

        return jdbcTemplate.queryForObject(StaffUpdateQuery.GET_STAFF_UPDATE, params,
                new StaffUpdateMapper());
    }

    @Override
    public void updateStaffUpdateStatus(StaffUpdate staffUpdate) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffUpdateId", staffUpdate.getStaffUpdateId())
                .addValue("staffUpdateStaffStatus", staffUpdate.getStaffUpdateStaffStatus());

        jdbcTemplate.update(StaffUpdateQuery.UPDATE_STAFF_UPDATE_STATUS, params);
    }
}
