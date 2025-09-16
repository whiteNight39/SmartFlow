package com.whitenight.smartflow.utils.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserPrincipal {
    private UUID userId;
    private String role;
    private UUID companyId;
}
