package com.whitenight.smartflow.model.entity;

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
public class StaffInvite {

    private UUID staffInviteId;
    private UUID staffInviteCSVUploadId;
    private String staffInviteEmail;
    private String staffInviteToken;
    private LocalDateTime staffInviteExpiresAt;

    private String staffInviteStatus;
    private LocalDateTime staffInviteCreatedAt;
    private LocalDateTime staffInviteUpdatedAt;
}
