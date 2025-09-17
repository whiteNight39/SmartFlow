package com.whitenight.smartflow.utils.jwt;

import com.whitenight.smartflow.utils.rank.StaffRole;
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
    private StaffRole role;
    private UUID companyId;
}
