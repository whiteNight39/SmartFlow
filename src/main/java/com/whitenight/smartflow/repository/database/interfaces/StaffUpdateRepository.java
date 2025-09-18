package com.whitenight.smartflow.repository.database.interfaces;

import com.whitenight.smartflow.model.entity.StaffUpdate;

import java.util.UUID;

public interface StaffUpdateRepository {

    void createStaffUpdate(StaffUpdate staffUpdate);
    StaffUpdate getStaffUpdate(UUID staffUpdateId);
    void updateStaffUpdateStatus(StaffUpdate staffUpdate);
}
