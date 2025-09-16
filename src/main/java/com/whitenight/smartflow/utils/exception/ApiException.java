package com.whitenight.smartflow.utils.exception;

import lombok.AllArgsConstructor;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private String errorCode;
    private String errorMessage;
    private Object errorData;
}
