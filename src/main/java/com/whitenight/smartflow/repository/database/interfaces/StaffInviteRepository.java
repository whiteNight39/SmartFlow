package com.whitenight.smartflow.repository.database.interfaces;

import com.whitenight.smartflow.model.entity.StaffInvite;

import java.util.List;
import java.util.UUID;

public interface StaffInviteRepository {

    void inviteStaff(StaffInvite staffInvite);
    List<StaffInvite> getStaffInvitesByStaffEmail(String staffEmail);
    void deleteStaffInvite(UUID staffInviteId);
}
