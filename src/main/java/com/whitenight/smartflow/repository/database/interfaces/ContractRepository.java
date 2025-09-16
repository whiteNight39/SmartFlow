package com.whitenight.smartflow.repository.database.interfaces;

import com.whitenight.smartflow.model.entity.Contract;

import java.util.UUID;

public interface ContractRepository {

    void createContract(Contract contract);
    Contract getContractById(UUID contractId);
    void updateContract(Contract contract);
    void deleteContract(UUID contractId);
}
