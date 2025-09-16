package com.whitenight.smartflow.utils.validator;

import com.whitenight.smartflow.model.entity.Staff;
import com.whitenight.smartflow.model.response.StaffAccountDetails;
import com.whitenight.smartflow.repository.database.interfaces.StaffRepository;
import com.whitenight.smartflow.utils.exception.ApiException;
import com.whitenight.smartflow.utils.rank.StaffRole;
import jakarta.validation.ValidationException;

import java.util.UUID;

public class StaffValidator {

    public static void validateStaff(Staff staff) {

        if (staff == null) {
            throw new ApiException("44", "Staff not found", null);
        }
    }

//    public static void validateStaff(StaffAccountDetails staff) {
//
//        if (staff == null) {
//            throw new ValidationException("Staff not found");
//        }
//    }
//
//    public static void validateStaff(UUID staff) {
//
//        if (staff == null) {
//            throw new ValidationException("Staff not found");
//        }
//    }

    public static void validateRoles(Staff staff, StaffRole role) {

        if (!staff.getStaffRole().equals(role)) {
            throw new ApiException("33", "Access denied. Required role: " + role, null);
        }
    }

    public static void validateRoles(StaffAccountDetails staff, String role) {

        if (!staff.getStaffRole().equals(role)) {
            throw new ValidationException("Access denied. Required role: " + role);
        }
    }
}
