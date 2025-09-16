package com.whitenight.smartflow.utils.rank;

import lombok.Getter;

@Getter
public enum StaffRole {
    DEVELOPER(5),
    SUPER_ADMIN(4),
    ADMIN(3),
    DEPT_HEAD(2),
    STAFF(1);

    private final int rank;

    StaffRole(int rank) {
        this.rank = rank;
    }

}