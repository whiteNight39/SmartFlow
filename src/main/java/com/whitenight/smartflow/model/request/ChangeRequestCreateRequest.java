package com.whitenight.smartflow.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRequestCreateRequest {

//    @NotNull
    private UUID changeRequestRequestedByStaffId;
    private UUID changeRequestRequestedBySupplierStaffId;
    @NotNull
    private UUID changeRequestHandledBy;
    @NotNull
    private UUID changeRequestSupplierId;
    @NotEmpty
    private String[] changeRequestOldData; // JSON string
    @NotEmpty
    private String[] changeRequestNewData; // JSON string
    @NotBlank
    private String changeRequestReason;
}
