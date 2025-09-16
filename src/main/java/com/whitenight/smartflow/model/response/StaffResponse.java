package com.whitenight.smartflow.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponse {

    private String staffFirstName;
    private String staffLastName;
    private String staffEmail;
    private String staffPhone;
    private String staffRole;
    private String staffJobTitle;
    private String staffSmartflowPersona;
    private String staffDepartment;
    private UUID staffWhoAddedId;
    private UUID staffCompanyId;
    private UUID staffDepartmentHeadId;
    private String staffAuthId;
    private Boolean staffActivated;
}
