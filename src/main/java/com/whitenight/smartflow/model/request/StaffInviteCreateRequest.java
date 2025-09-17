package com.whitenight.smartflow.model.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffInviteCreateRequest {

//    @NotNull
    private UUID staffInviteCSVUploadId;
    @NotBlank
    private String staffInviteEmail;
    @NotBlank
    private String staffInviteToken;
    @NotNull
    @Future
    private Instant staffInviteExpiresAt;
}