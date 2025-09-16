package com.whitenight.smartflow.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAPIResponse<T> {

    private String companyAPIResponseCode;
    private String companyAPIResponseMessage;
    private T companyAPIResponseData;
}
