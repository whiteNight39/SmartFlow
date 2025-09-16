package com.whitenight.smartflow.repository.database.interfaces;

import com.whitenight.smartflow.model.entity.Staff;
import com.whitenight.smartflow.model.response.StaffAccountDetails;
import com.whitenight.smartflow.model.response.StaffResponse;
import com.whitenight.smartflow.model.response.StaffSignInResponse;

import java.util.UUID;

public interface StaffRepository {

    void addStaff(Staff staff);
    void updateStaff(Staff staff);
    Staff getStaffById(UUID staffId);
    UUID getStaffDepartmentHeadId(UUID staffCompanyId, String staffDepartment);
    StaffAccountDetails getStaffByEmail(String staffEmail);
    void deleteStaff(UUID staffId);
}
