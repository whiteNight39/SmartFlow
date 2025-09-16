package com.whitenight.smartflow.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffJwtAuth {

    private UUID staffJwtAuthId;
    private UUID staffJwtAuthStaffId;

    private String staffJwtAuthJwtToken;

    private Date staffJwtAuthIssuedAt;
    private Date staffJwtAuthExpiresAt;

    private Boolean staffJwtAuthIsValid;

    private String staffJwtAuthUserDeviceIp;
    private String staffJwtAuthUserDeviceAgent;
    private String staffJwtAuthLevel;
}
