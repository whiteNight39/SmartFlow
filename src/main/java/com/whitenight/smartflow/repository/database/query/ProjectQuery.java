package com.whitenight.smartflow.repository.database.query;

public class ProjectQuery {

    public static final String CREATE_PROJECT = """
    INSERT INTO Project (
        project_company_id,
        project_type,
        project_created_by_id,
        project_supplier_id,
        project_assigned_sm_id,
        project_sm_approved,
        project_sm_approval_notes,
        project_dept_head_id,
        project_final_authorised,
        project_final_approval_notes,
        project_contract_start_date,
        project_contract_end_date,
        project_contract_currency,
        project_contract_total_amount,
        project_rfx_currency,
        project_rfx_deadline,
        project_rfx_payment_split,
        project_status,
        project_created_at,
        project_updated_at
    ) VALUES (
        :projectCompanyId,
        :projectType,
        :projectCreatedById,
        :projectSupplierId,
        :projectAssignedSmId,
        :projectSmApproved,
        :projectSmApprovalNotes,
        :projectDeptHeadId,
        :projectFinalAuthorised,
        :projectFinalApprovalNotes,
        :projectContractStartDate,
        :projectContractEndDate,
        :projectContractCurrency,
        :projectContractTotalAmount,
        :projectRfxCurrency,
        :projectRfxDeadline,
        :projectRfxPaymentSplit,
        'PENDING',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    )
    RETURNING project_id;
""";

    public static final String GET_PROJECT_BY_ID = """
    SELECT
        project_id,
        project_company_id,
        project_type,
        project_created_by_id,
        project_supplier_id,
        project_assigned_sm_id,
        project_sm_approved,
        project_sm_approval_notes,
        project_dept_head_id,
        project_final_authorised,
        project_final_approval_notes,
        project_contract_start_date,
        project_contract_end_date,
        project_contract_currency,
        project_contract_total_amount,
        project_rfx_currency,
        project_rfx_deadline,
        project_rfx_payment_split,
        project_status,
        project_created_at,
        project_updated_at
    FROM Project
    WHERE project_id = :projectId
      AND project_status != 'DELETED';
""";

    public static final String UPDATE_PROJECT = """
    UPDATE Project
    SET
        project_type = COALESCE(NULLIF(:projectType, ''), project_type),
        project_supplier_id = COALESCE(:projectSupplierId, project_supplier_id),
        project_assigned_sm_id = COALESCE(:projectAssignedSmId, project_assigned_sm_id),
        project_sm_approved = COALESCE(:projectSmApproved, project_sm_approved),
        project_sm_approval_notes = COALESCE(NULLIF(:projectSmApprovalNotes, ''), project_sm_approval_notes),
        project_dept_head_id = COALESCE(:projectDeptHeadId, project_dept_head_id),
        project_final_authorised = COALESCE(:projectFinalAuthorised, project_final_authorised),
        project_final_approval_notes = COALESCE(NULLIF(:projectFinalApprovalNotes, ''), project_final_approval_notes),
        project_contract_start_date = COALESCE(:projectContractStartDate, project_contract_start_date),
        project_contract_end_date = COALESCE(:projectContractEndDate, project_contract_end_date),
        project_contract_currency = COALESCE(NULLIF(:projectContractCurrency, ''), project_contract_currency),
        project_contract_total_amount = COALESCE(:projectContractTotalAmount, project_contract_total_amount),
        project_rfx_currency = COALESCE(NULLIF(:projectRfxCurrency, ''), project_rfx_currency),
        project_rfx_deadline = COALESCE(:projectRfxDeadline, project_rfx_deadline),
        project_rfx_payment_split = COALESCE(NULLIF(:projectRfxPaymentSplit, ''), project_rfx_payment_split),
        project_status = COALESCE(NULLIF(:projectStatus, ''), project_status),
        project_updated_at = CURRENT_TIMESTAMP
    WHERE project_id = :projectId;
""";

    public static final String DELETE_PROJECT = """
    UPDATE Project
    SET project_status = 'DELETED',
        project_updated_at = CURRENT_TIMESTAMP
    WHERE project_id = :projectId;
""";

//    public static final String ASSIGN_PROJECT_TO_STAFF = """
//    UPDATE Project
//    SET project_assigned_sm_id = :staffId,
//        project_updated_at = CURRENT_TIMESTAMP
//    WHERE project_id = :projectId
//      AND project_status != 'DELETED';
//""";
//
//    public static final String APPROVE_PROJECT = """
//    UPDATE Project
//    SET
//        project_sm_approved = :projectSmApproved,
//        project_sm_approval_notes = :projectSmApprovalNotes,
//        project_status = CASE
//            WHEN :projectSmApproved = TRUE THEN 'SM_APPROVED'
//            ELSE 'REJECTED'
//        END,
//        project_updated_at = CURRENT_TIMESTAMP
//    WHERE project_id = :projectId
//      AND project_status != 'DELETED';
//""";
//
//    public static final String AUTHORISE_PROJECT = """
//    UPDATE Project
//    SET
//        project_final_authorised = :projectFinalAuthorised,
//        project_final_approval_notes = :projectFinalApprovalNotes,
//        project_status = CASE
//            WHEN :projectFinalAuthorised = TRUE THEN 'FINAL_AUTHORISED'
//            ELSE 'REJECTED'
//        END,
//        project_updated_at = CURRENT_TIMESTAMP
//    WHERE project_id = :projectId
//      AND project_status = 'SM_APPROVED';
//""";
}
