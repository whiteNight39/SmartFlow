package com.whitenight.smartflow.repository.database.query;

public class CSVUploadQuery {

    public static final String UPLOAD_STAFF_CSV = """
    INSERT INTO SMARTFLOW_CSVUpload (
        csv_upload_uploaded_by,
        csv_upload_file_name,
        csv_upload_num_entries,
        csv_upload_assigned_to,
        csv_upload_approved,
        
        csv_upload_file,
        csv_upload_status,
        csv_upload_created_at
    ) VALUES (
        :csvUploadUploadedBy,
        :csvUploadFileName,
        :csvUploadNumEntries,
        :csvUploadAssignedTo,
        'FALSE',
        
        :csvUploadFile,
        'ACTIVE',
        CURRENT_TIMESTAMP
    )
    RETURNING csv_upload_id;
""";

    public static final String UPDATE_STAFF_CSV = """
    UPDATE SMARTFLOW_CSVUpload
    SET
        csv_upload_file_name         = COALESCE(NULLIF(:csvUploadFileName, ''), csv_upload_file_name),
        csv_upload_assigned_to       = COALESCE(:csvUploadAssignedTo, csv_upload_assigned_to),
        csv_upload_approved          = COALESCE(:csvUploadApproved, csv_upload_approved),
        csv_upload_approval_notes    = COALESCE(NULLIF(:csvUploadApprovalNotes, ''), csv_upload_approval_notes),
        csv_upload_updated_at        = CURRENT_TIMESTAMP
    WHERE csv_upload_id = :csvUploadId;
        AND csv_upload_approved != TRUE
""";

    public static final String GET_CSV_UPLOAD_BY_ID = """
    SELECT
        csv_upload_id,
        csv_upload_uploaded_by,
        csv_upload_file_name,
        csv_upload_num_entries,
        csv_upload_assigned_to,
        csv_upload_approved,
        csv_upload_approval_notes,
        csv_upload_file,
        csv_upload_status,
        csv_upload_created_at
    FROM SMARTFLOW_CSVUpload
    WHERE csv_upload_id = :csvUploadId
      AND csv_upload_status = 'ACTIVE';
""";

    public static final String DELETE_CSV_UPLOAD_BY_ID = """
    UPDATE SMARTFLOW_CSVUpload
    SET csv_upload_status = 'DELETED',
        csv_upload_updated_at = CURRENT_TIMESTAMP
    WHERE csv_upload_id = :csvUploadId
      AND csv_upload_approved = FALSE;
""";

}
