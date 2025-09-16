package com.whitenight.smartflow.repository.database.query;

public class ChangeRequestQuery {

    public static final String CREATE_CHANGE_REQUEST = """
    INSERT INTO SMARTFLOW_ChangeRequest (
        change_request_requested_by_staff_id,
        change_request_requested_by_supplier_staff_id,
        change_request_handled_by,
        change_request_supplier_id,
        change_request_old_data,
        change_request_new_data,
        change_request_reason,
        change_request_status,
        change_request_created_at,
        change_request_sm_approved,
        change_request_sm_approval_notes
    ) VALUES (
        :changeRequestRequestedByStaffId,
        :changeRequestRequestedBySupplierStaffId,
        :changeRequestHandledBy,
        :changeRequestSupplierId,
        :changeRequestOldData,
        :changeRequestNewData,
        :changeRequestReason,
        'PENDING',
        CURRENT_TIMESTAMP,
        :changeRequestSmApproved,
        :changeRequestSmApprovalNotes
    )
    RETURNING change_request_id;
""";

    public static final String GET_CHANGE_REQUEST_BY_ID = """
    SELECT
        change_request_id,
        change_request_requested_by_staff_id,
        change_request_requested_by_supplier_staff_id,
        change_request_handled_by,
        change_request_supplier_id,
        change_request_old_data,
        change_request_new_data,
        change_request_reason,
        change_request_status,
        change_request_created_at,
        change_request_resolved_at,
        change_request_sm_approved,
        change_request_sm_approval_notes
    FROM SMARTFLOW_ChangeRequest
    WHERE change_request_id = :changeRequestId
      AND change_request_status != 'DELETED';
""";

    public static final String UPDATE_CHANGE_REQUEST = """
    UPDATE SMARTFLOW_ChangeRequest
    SET
        change_request_handled_by = COALESCE(:changeRequestHandledBy, change_request_handled_by),
        change_request_old_data = COALESCE(:changeRequestOldData, change_request_old_data),
        change_request_new_data = COALESCE(:changeRequestNewData, change_request_new_data),
        change_request_reason = COALESCE(NULLIF(:changeRequestReason, ''), change_request_reason),
        change_request_sm_approved = COALESCE(:changeRequestSmApproved, change_request_sm_approved),
        change_request_sm_approval_notes = COALESCE(NULLIF(:changeRequestSmApprovalNotes, ''), change_request_sm_approval_notes),
        change_request_status = COALESCE(NULLIF(:changeRequestStatus, ''), change_request_status),
        change_request_resolved_at = COALESCE(:changeRequestResolvedAt, change_request_resolved_at),
        change_request_supplier_id = COALESCE(:changeRequestSupplierId, change_request_supplier_id)
    WHERE change_request_id = :changeRequestId;
""";

    public static final String DELETE_CHANGE_REQUEST = """
    UPDATE SMARTFLOW_ChangeRequest
    SET change_request_status = 'DELETED'
    WHERE change_request_id = :changeRequestId;
""";

//    public static final String ASSIGN_CHANGE_REQUEST_TO_STAFF = """
//    UPDATE SMARTFLOW_ChangeRequest
//    SET change_request_handled_by = :staffId
//    WHERE change_request_id = :changeRequestId
//      AND change_request_status = 'PENDING';
//""";
//
//    public static final String APPROVE_CHANGE_REQUEST = """
//    UPDATE SMARTFLOW_ChangeRequest
//    SET
//        change_request_sm_approved = :changeRequestSmApproved,
//        change_request_sm_approval_notes = :changeRequestSmApprovalNotes,
//        change_request_status = CASE
//            WHEN :changeRequestSmApproved = TRUE THEN 'SM_APPROVED'
//            ELSE 'REJECTED'
//        END,
//        change_request_resolved_at = CURRENT_TIMESTAMP
//    WHERE change_request_id = :changeRequestId
//      AND change_request_status = 'PENDING';
//""";
}
