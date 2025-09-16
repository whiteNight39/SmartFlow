package com.whitenight.smartflow.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whitenight.smartflow.model.enums.Status;
import com.whitenight.smartflow.utils.rank.StaffRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    private UUID staffId;
    private String staffFirstName;
    private String staffLastName;
    private String staffEmail;
    @JsonIgnore
    private String staffPassword;
    private String staffPhone;
    private StaffRole staffRole;
    private String staffJobTitle;
    private String staffSmartflowPersona;
    private String staffDepartment;
    private UUID staffWhoAddedId;
    private UUID staffCompanyId;
    private UUID staffDepartmentHeadId;
    private String staffAuthId;
    private Boolean staffActivated;

    private Status staffStatus;
    private LocalDateTime staffCreatedAt;
    private LocalDateTime staffUpdatedAt;
}
