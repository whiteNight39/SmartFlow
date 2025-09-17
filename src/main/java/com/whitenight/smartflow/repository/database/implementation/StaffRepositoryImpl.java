package com.whitenight.smartflow.repository.database.implementation;

import com.whitenight.smartflow.mapper.StaffMapper;
import com.whitenight.smartflow.model.entity.Staff;
import com.whitenight.smartflow.model.response.StaffResponse;
import com.whitenight.smartflow.repository.database.interfaces.StaffRepository;
import com.whitenight.smartflow.repository.database.query.StaffQuery;
import com.whitenight.smartflow.utils.rank.StaffRole;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class StaffRepositoryImpl implements StaffRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StaffRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addStaff(Staff staff) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffFirstName", staff.getStaffFirstName())
                .addValue("staffLastName", staff.getStaffLastName())
                .addValue("staffEmail", staff.getStaffEmail())
                .addValue("staffPassword", staff.getStaffPassword())
                .addValue("staffPhone", staff.getStaffPhone())
                .addValue("staffRole", staff.getStaffRole())
                .addValue("staffJobTitle", staff.getStaffJobTitle())
                .addValue("staffSmartflowPersona", staff.getStaffSmartflowPersona())
                .addValue("staffDepartment", staff.getStaffDepartment())
                .addValue("staffWhoAddedId", staff.getStaffWhoAddedId())
                .addValue("staffCompanyId", staff.getStaffCompanyId())
                .addValue("staffDepartmentHeadId", staff.getStaffDepartmentHeadId());

        jdbcTemplate.update(StaffQuery.ADD_STAFF, params);
    }

    @Override
    public void updateStaff(Staff staff) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffId", staff.getStaffId())
                .addValue("staffFirstName", staff.getStaffFirstName())
                .addValue("staffLastName", staff.getStaffLastName())
                .addValue("staffEmail", staff.getStaffEmail())
                .addValue("staffPhone", staff.getStaffPhone())
                .addValue("staffRole", staff.getStaffRole())
                .addValue("staffJobTitle", staff.getStaffJobTitle())
                .addValue("staffSmartflowPersona", staff.getStaffSmartflowPersona())
                .addValue("staffDepartment", staff.getStaffDepartment())
                .addValue("staffWhoAddedId", staff.getStaffWhoAddedId())
                .addValue("staffCompanyId", staff.getStaffCompanyId())
                .addValue("staffDepartmentHeadId", staff.getStaffDepartmentHeadId())
                .addValue("staffAuthId", staff.getStaffAuthId())
                .addValue("staffActivated", staff.getStaffActivated())
                .addValue("staffPassword", staff.getStaffPassword());

        jdbcTemplate.update(StaffQuery.UPDATE_STAFF, params);
    }

    @Override
    public Staff getStaffById(UUID staffId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffId", staffId);

        List<Staff> staffs = jdbcTemplate.query(StaffQuery.GET_STAFF_BY_ID, params,
                new StaffMapper());

        return staffs.isEmpty() ? null : staffs.getFirst();
    }

    @Override
    public UUID getStaffDepartmentHeadId(UUID staffCompanyId, String staffDepartment) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffCompanyId", staffCompanyId)
                .addValue("staffDepartment", staffDepartment);

        List<UUID> uuids = jdbcTemplate.query(StaffQuery.GET_STAFF_DEPARTMENT_HEAD_ID, params,
                (rs, rowNum) -> UUID.fromString(rs.getString("staff_id")));
        return uuids.isEmpty() ? null : uuids.getFirst();
    }

    @Override
    public Staff getStaffByEmail(String staffEmail) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffEmail", staffEmail);

        List<Staff> staffs = jdbcTemplate.query(StaffQuery.GET_STAFF_BY_EMAIL, params, new StaffMapper());

        return staffs.isEmpty() ? null : staffs.getFirst();
    }

    @Override
    public void deleteStaff(UUID staffId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("staffId", staffId);

        jdbcTemplate.update(StaffQuery.DELETE_STAFF, params);
    }

    private UUID getUuidOrNull(ResultSet rs, String columnLabel) throws SQLException {
        String value = rs.getString(columnLabel);
        return value == null ? null : UUID.fromString(value);
    }
}
