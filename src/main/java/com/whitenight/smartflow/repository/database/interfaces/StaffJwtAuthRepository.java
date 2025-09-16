package com.whitenight.smartflow.repository.database.interfaces;

import com.whitenight.smartflow.model.entity.StaffJwtAuth;

import java.util.UUID;

public interface StaffJwtAuthRepository {

    void createStaffJwtAuth(StaffJwtAuth staffJwtAuth);
    StaffJwtAuth getStaffJwtAuth(UUID staffJwtAuthStaffId, String ip, String userAgent);
}
