package com.whitenight.smartflow.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
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
    private Instant staffInviteExpiresAt;

    private String staffInviteStatus;
    private Instant staffInviteCreatedAt;
    private Instant staffInviteUpdatedAt;
}
