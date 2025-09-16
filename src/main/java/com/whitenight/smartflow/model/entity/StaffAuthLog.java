package com.whitenight.smartflow.model.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffAuthLog {

    private UUID staffAuthLogId;

    private UUID staffAuthLogStaffId;

    private String staffAuthLogStaffLevel;

    private LocalDateTime staffAuthLogLastAuthorizationDate;
}
