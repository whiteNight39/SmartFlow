package com.whitenight.smartflow.repository.database.implementation;

import com.whitenight.smartflow.mapper.SupplierStaffMapper;
import com.whitenight.smartflow.model.entity.SupplierStaff;
import com.whitenight.smartflow.model.response.SupplierStaffResponse;
import com.whitenight.smartflow.repository.database.interfaces.SupplierStaffRepository;
import com.whitenight.smartflow.repository.database.query.SupplierStaffQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SupplierStaffRepositoryImpl implements SupplierStaffRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SupplierStaffRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addSupplierStaff(SupplierStaff supplierStaff) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("supplierStaffSupplierId", supplierStaff.getSupplierStaffSupplierId())
                .addValue("supplierStaffFirstName", supplierStaff.getSupplierStaffFirstName())
                .addValue("supplierStaffLastName", supplierStaff.getSupplierStaffLastName())
                .addValue("supplierStaffEmail", supplierStaff.getSupplierStaffEmail())
                .addValue("supplierStaffPhone", supplierStaff.getSupplierStaffPhone())
                .addValue("supplierStaffRole", supplierStaff.getSupplierStaffRole())
                .addValue("supplierStaffInvitedByStaffId", supplierStaff.getSupplierStaffInvitedByStaffId())
                .addValue("supplierStaffInvitedBySupplierStaffId", supplierStaff.getSupplierStaffInvitedBySupplierStaffId());

        jdbcTemplate.update(SupplierStaffQuery.ADD_SUPPLIER_STAFF, params);
    }

    @Override
    public SupplierStaff getSupplierStaff(UUID supplierStaffId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("supplierStaffId", supplierStaffId);

        return jdbcTemplate.queryForObject(SupplierStaffQuery.GET_SUPPLIER_STAFF_BY_ID, params, new SupplierStaffMapper());
    }

    @Override
    public void updateSupplierStaff(SupplierStaff supplierStaff) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("supplierStaffId", supplierStaff.getSupplierStaffId())
                .addValue("supplierStaffFirstName", supplierStaff.getSupplierStaffFirstName())
                .addValue("supplierStaffLastName", supplierStaff.getSupplierStaffLastName())
                .addValue("supplierStaffEmail", supplierStaff.getSupplierStaffEmail())
                .addValue("supplierStaffPhone", supplierStaff.getSupplierStaffPhone())
                .addValue("supplierStaffRole", supplierStaff.getSupplierStaffRole())
                .addValue("supplierStaffInvitedByStaffId", supplierStaff.getSupplierStaffInvitedByStaffId())
                .addValue("supplierStaffInvitedBySupplierStaffId",  supplierStaff.getSupplierStaffInvitedBySupplierStaffId());

        jdbcTemplate.update(SupplierStaffQuery.UPDATE_SUPPLIER_STAFF, params);
    }


    @Override
    public void deleteSupplierStaff(UUID supplierStaffId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("supplierStaffId", supplierStaffId);

        jdbcTemplate.update(SupplierStaffQuery.DELETE_SUPPLIER_STAFF, params);
    }

}
