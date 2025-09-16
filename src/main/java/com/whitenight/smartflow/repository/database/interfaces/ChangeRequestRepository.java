package com.whitenight.smartflow.repository.database.interfaces;

import com.whitenight.smartflow.model.entity.ChangeRequest;

import java.util.UUID;

public interface ChangeRequestRepository {

    void createChangeRequest(ChangeRequest changeRequest);
    ChangeRequest getChangeRequest(UUID changeRequestId);
    void updateChangeRequest(ChangeRequest changeRequest);
    void deleteChangeRequest(UUID changeRequestId);
//    void assignChangeRequestToStaff(UUID changeRequestId, UUID staffId);
//    void approveChangeRequest(UUID changeRequestId, Boolean changeRequestSmApproved,  String changeRequestSmApprovalNotes);

}
