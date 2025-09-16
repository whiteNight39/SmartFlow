package com.whitenight.smartflow.repository.database.interfaces;

import com.whitenight.smartflow.model.entity.StaffAuthLog;

import java.util.UUID;

public interface StaffAuthLogRepository {

    void addStaffAuthLog(StaffAuthLog staffAuthLog);
    StaffAuthLog getStaffAuthLogById(UUID staffAuthLogId);
}
