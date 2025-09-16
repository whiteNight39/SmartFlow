package com.whitenight.smartflow.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffCreateRequest {

    @NotBlank
    private String staffFirstName;
    @NotBlank
    private String staffLastName;
    @NotBlank
    @Email
    private String staffEmail;
    @NotBlank
    private String staffPhone;
    @NotBlank
    private String staffRole;
    @NotBlank
    private String staffJobTitle;
//    @NotBlank
    private String staffSmartflowPersona;
    @NotBlank
    private String staffDepartment;
//    @NotNull
//    private UUID staffWhoAddedId;
//    @NotNull
//    private UUID staffCompanyId;
//    @NotNull
//    private UUID staffDepartmentHeadId;
    private String staffAuthId;
}
