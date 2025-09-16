package com.whitenight.smartflow.repository.database.implementation;

import com.whitenight.smartflow.mapper.ContractMapper;
import com.whitenight.smartflow.model.entity.Contract;
import com.whitenight.smartflow.repository.database.interfaces.ContractRepository;
import com.whitenight.smartflow.repository.database.query.ContractQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ContractRepositoryImpl implements ContractRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ContractRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createContract(Contract contract) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("contractProjectId", contract.getContractProjectId())
                .addValue("contractSupplierId", contract.getContractSupplierId())
                .addValue("contractTitle", contract.getContractTitle())
                .addValue("contractReferenceNumber", contract.getContractReferenceNumber())
                .addValue("contractCurrency", contract.getContractCurrency())
                .addValue("contractTotalAmount", contract.getContractTotalAmount())
                .addValue("contractStartDate", contract.getContractStartDate())
                .addValue("contractEndDate", contract.getContractEndDate())
                .addValue("contractStatus", contract.getContractStatus())
                .addValue("contractBriefFile", contract.getContractBriefFile());

        UUID contractId = jdbcTemplate.queryForObject(ContractQuery.CREATE_CONTRACT, params, UUID.class);
        contract.setContractId(contractId); // if you want to retain the created ID in the object
    }

    @Override
    public Contract getContractById(UUID contractId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("contractId", contractId);

        return jdbcTemplate.queryForObject(
                ContractQuery.GET_CONTRACT_BY_ID,
                params,
                new ContractMapper()
        );
    }

    @Override
    public void updateContract(Contract contract) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("contractId", contract.getContractId())
                .addValue("contractTitle", contract.getContractTitle())
                .addValue("contractReferenceNumber", contract.getContractReferenceNumber())
                .addValue("contractStatus", contract.getContractStatus());

        jdbcTemplate.update(ContractQuery.UPDATE_CONTRACT, params);
    }


    @Override
    public void deleteContract(UUID contractId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("contractId", contractId);

        jdbcTemplate.update(ContractQuery.DELETE_CONTRACT, params);
    }
}
