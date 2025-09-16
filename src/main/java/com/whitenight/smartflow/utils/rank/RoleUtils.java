package com.whitenight.smartflow.utils.rank;

public class RoleUtils {

    public static boolean canAdd(StaffRole addingRole, StaffRole addedRole) {
        return addingRole.getRank() > addedRole.getRank();
    }
}