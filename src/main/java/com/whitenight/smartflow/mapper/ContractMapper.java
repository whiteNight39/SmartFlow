package com.whitenight.smartflow.mapper;

import com.whitenight.smartflow.model.entity.Contract;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class ContractMapper implements RowMapper<Contract> {

    @Override
    public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Contract.builder()
                .contractId(UUID.fromString(rs.getString("contract_id")))
                .contractProjectId(UUID.fromString(rs.getString("contract_project_id")))
                .contractSupplierId(UUID.fromString(rs.getString("contract_supplier_id")))
                .contractTitle(rs.getString("contract_title"))
                .contractReferenceNumber(rs.getString("contract_reference_number"))
                .contractCurrency(rs.getString("contract_currency"))
                .contractTotalAmount(rs.getDouble("contract_total_amount"))
                .contractStartDate(rs.getTimestamp("contract_start_date").toLocalDateTime())
                .contractEndDate(rs.getTimestamp("contract_end_date").toLocalDateTime())
                .contractStatus(rs.getString("contract_status"))
                .contractBriefFile(rs.getBytes("contract_brief_file"))
                .build();
    }
}