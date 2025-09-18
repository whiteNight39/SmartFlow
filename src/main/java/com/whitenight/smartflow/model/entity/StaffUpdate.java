package com.whitenight.smartflow.model.entity;

import com.whitenight.smartflow.model.enums.Status;
import com.whitenight.smartflow.utils.rank.StaffRole;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffUpdate {

    private UUID staffUpdateId;
    private UUID staffUpdateStaffId;
    private String staffUpdateStaffFirstName;
    private String staffUpdateStaffLastName;
    @Email
    private String staffUpdateStaffEmail;
    private String staffUpdateStaffPhone;
    private StaffRole staffUpdateStaffRole;
    private String staffUpdateStaffJobTitle;
    private String staffUpdateStaffSmartflowPersona;
    private String staffUpdateStaffDepartment;
    private UUID staffUpdateStaffDepartmentHeadId;
    private String staffUpdateStaffAuthId;
    private  String staffUpdateStaffPassword;
    private Status staffUpdateStaffStatus;
}
