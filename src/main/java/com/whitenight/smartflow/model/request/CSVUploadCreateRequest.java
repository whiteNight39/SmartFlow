package com.whitenight.smartflow.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CSVUploadCreateRequest {

    @NotNull
    private UUID csvUploadUploadedBy;
    @NotBlank
    private String csvUploadFileName;
    @NotNull
    private Integer csvUploadNumEntries;
    @NotNull
    private UUID csvUploadAssignedTo;

    @NotNull
    private byte[] csvUploadFile;
}
