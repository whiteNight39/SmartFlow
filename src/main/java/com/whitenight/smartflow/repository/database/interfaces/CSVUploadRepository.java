package com.whitenight.smartflow.repository.database.interfaces;

import com.whitenight.smartflow.model.entity.CSVUpload;

import java.util.UUID;

public interface CSVUploadRepository {

    void uploadStaffCSV(CSVUpload csvUpload);
    void updateStaffCSV(CSVUpload csvUpload);
//    void reassignCSVUpload(UUID staffId, UUID csvUploadId);
    CSVUpload getCSVUploadById(UUID csvUploadId);
    void deleteCSVUploadById(UUID csvUploadId);
}
