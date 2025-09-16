package com.whitenight.smartflow.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffUpdateRequest {

    private UUID staffId;
    private String staffFirstName;
    private String staffLastName;
    @Email
    private String staffEmail;
    private String staffPhone;
    private String staffRole;
    private String staffJobTitle;
    private String staffSmartflowPersona;
    private String staffDepartment;
    private UUID staffDepartmentHeadId;
    private String staffAuthId;
    private  String staffPassword;
    private String staffStatus;
}
