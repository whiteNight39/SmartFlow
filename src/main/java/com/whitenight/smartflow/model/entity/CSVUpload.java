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
public class CSVUpload {

    private UUID csvUploadId;
    private UUID csvUploadUploadedBy;
    private String csvUploadFileName;
    private Integer csvUploadNumEntries;
    private UUID csvUploadAssignedTo;
    private Boolean csvUploadApproved;
    private String csvUploadApprovalNotes;

    private String csvUploadStatus;
    private LocalDateTime csvUploadCreatedAt;

    private byte[] csvUploadFile;
}
