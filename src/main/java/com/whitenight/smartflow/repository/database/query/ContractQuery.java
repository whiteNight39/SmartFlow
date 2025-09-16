package com.whitenight.smartflow.repository.database.query;

public class ContractQuery {

    public static final String CREATE_CONTRACT = """
    INSERT INTO SMARTFLOW_Contract (
        contract_project_id,
        contract_supplier_id,
        contract_title,
        contract_reference_number,
        contract_currency,
        contract_total_amount,
        contract_start_date,
        contract_end_date,
        contract_status,
        contract_brief_file
    ) VALUES (
        :contractProjectId,
        :contractSupplierId,
        :contractTitle,
        :contractReferenceNumber,
        :contractCurrency,
        :contractTotalAmount,
        :contractStartDate,
        :contractEndDate,
        :contractStatus,
        :contractBriefFile
    )
    RETURNING contract_id;
""";

    public static final String GET_CONTRACT_BY_ID = """
    SELECT
        contract_id,
        contract_project_id,
        contract_supplier_id,
        contract_title,
        contract_reference_number,
        contract_currency,
        contract_total_amount,
        contract_start_date,
        contract_end_date,
        contract_status,
        contract_brief_file
    FROM SMARTFLOW_Contract
    WHERE contract_id = :contractId;
""";

    public static final String UPDATE_CONTRACT = """
    UPDATE SMARTFLOW_Contract
    SET
        contract_title = COALESCE(NULLIF(:contractTitle, ''), contract_title),
        contract_reference_number = COALESCE(NULLIF(:contractReferenceNumber, ''), contract_reference_number),
        contract_status = COALESCE(NULLIF(:contractStatus, ''), contract_status)
    WHERE contract_id = :contractId;
""";

    public static final String DELETE_CONTRACT = """
    UPDATE SMARTFLOW_Contract
    SET contract_status = 'TERMINATED'
    WHERE contract_id = :contractId;
""";
}
