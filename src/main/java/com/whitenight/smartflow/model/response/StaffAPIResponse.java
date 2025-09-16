package com.whitenight.smartflow.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffAPIResponse<T> {

    private String staffAPIResponseCode;
    private String staffAPIResponseMessage;
    private T staffAPIResponseData;
}
