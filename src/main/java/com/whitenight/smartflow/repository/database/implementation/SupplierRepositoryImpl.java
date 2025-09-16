package com.whitenight.smartflow.repository.database.implementation;

import com.whitenight.smartflow.mapper.SupplierResponseExtractor;
import com.whitenight.smartflow.model.entity.Supplier;
import com.whitenight.smartflow.model.response.SupplierResponse;
import com.whitenight.smartflow.repository.database.interfaces.SupplierRepository;
import com.whitenight.smartflow.repository.database.query.SupplierQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SupplierRepositoryImpl implements SupplierRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SupplierRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addSupplier(Supplier supplier) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("supplierCompanyId", supplier.getSupplierCompanyId())
                .addValue("supplierName", supplier.getSupplierName())
                .addValue("supplierAddress", supplier.getSupplierAddress())
                .addValue("supplierPhone", supplier.getSupplierPhone())
                .addValue("supplierEmail", supplier.getSupplierEmail())
                .addValue("supplierState", supplier.getSupplierState())
                .addValue("supplierCountry", supplier.getSupplierCountry())
                .addValue("supplierContactPersonFirstName", supplier.getSupplierContactPersonFirstName())
                .addValue("supplierContactPersonLastName", supplier.getSupplierContactPersonLastName())
                .addValue("supplierContactPersonEmail", supplier.getSupplierContactPersonEmail())
                .addValue("supplierContactPersonPhone", supplier.getSupplierContactPersonPhone())
                .addValue("supplierAddedBy", supplier.getSupplierAddedBy());

        jdbcTemplate.update(SupplierQuery.ADD_SUPPLIER, params);
    }

    @Override
    public SupplierResponse getSupplierById(UUID supplierId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("supplierId", supplierId);

        return jdbcTemplate.query(SupplierQuery.GET_SUPPLIER_BY_ID, params,
                new SupplierResponseExtractor());
    }

    @Override
    public void updateSupplier(Supplier supplier) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("supplierId", supplier.getSupplierId())
                .addValue("supplierName", supplier.getSupplierName())
                .addValue("supplierAddress", supplier.getSupplierAddress())
                .addValue("supplierPhone", supplier.getSupplierPhone())
                .addValue("supplierEmail", supplier.getSupplierEmail())
                .addValue("supplierState", supplier.getSupplierState())
                .addValue("supplierCountry", supplier.getSupplierCountry())
                .addValue("supplierContactPersonFirstName", supplier.getSupplierContactPersonFirstName())
                .addValue("supplierContactPersonLastName", supplier.getSupplierContactPersonLastName())
                .addValue("supplierContactPersonEmail", supplier.getSupplierContactPersonEmail())
                .addValue("supplierContactPersonPhone", supplier.getSupplierContactPersonPhone())
                .addValue("supplierAddedBy", supplier.getSupplierAddedBy());

        jdbcTemplate.update(SupplierQuery.UPDATE_SUPPLIER, params);
    }

    @Override
    public void deleteSupplierById(String supplierId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("supplierId", supplierId);

        jdbcTemplate.update(SupplierQuery.DELETE_SUPPLIER_BY_ID, params);
    }
}
