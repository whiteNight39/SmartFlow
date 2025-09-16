package com.whitenight.smartflow.repository.database.implementation;

import com.whitenight.smartflow.mapper.StaffAuthLogMapper;
import com.whitenight.smartflow.model.entity.StaffAuthLog;
import com.whitenight.smartflow.repository.database.interfaces.StaffAuthLogRepository;
import com.whitenight.smartflow.repository.database.query.StaffAuthLogQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class StaffAuthLogRepositoryImpl implements StaffAuthLogRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StaffAuthLogRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addStaffAuthLog(StaffAuthLog staffAuthLog) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffAuthLogStaffId", staffAuthLog.getStaffAuthLogStaffId())
                .addValue("staffAuthLogStaffLevel", staffAuthLog.getStaffAuthLogStaffLevel());

        jdbcTemplate.update(StaffAuthLogQuery.ADD_STAFF_AUTH_LOG, params);
    }

    @Override
    public StaffAuthLog getStaffAuthLogById(UUID staffAuthLogId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffAuthLogId", staffAuthLogId);

        List<StaffAuthLog> results = jdbcTemplate.query(StaffAuthLogQuery.GET_STAFF_AUTH_LOG_BY_ID,params,
                new StaffAuthLogMapper());

        return results.isEmpty() ? null : results.getFirst();
    }
}
