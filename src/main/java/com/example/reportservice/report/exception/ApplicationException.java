package com.example.reportservice.report.exception;

import com.example.reportservice.report.response.ErrorResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1138067521792343348L;
    private final String code;
    private final String message;
    private final HttpStatus status;
    private final Object detailError;
    private final Throwable rootCause;

    public ApplicationException(ErrorResponse errorResponse) {
        this(errorResponse.getCode(), errorResponse.getMessage(), errorResponse.getHttpStatus());
    }

    public ApplicationException(ErrorResponse errorResponse, String customMessage) {
        this(errorResponse.getCode(), customMessage, errorResponse.getHttpStatus());
    }

    public ApplicationException(String errorCode, String message, HttpStatus status) {
        this(errorCode, message, status, null, null);
    }

    public ApplicationException(String errorCode, String message, HttpStatus status, Object detail) {
        this(errorCode, message, status, detail, null);
    }

    public ApplicationException(String errorCode, String message, HttpStatus status, Object detail,
                                Throwable cause) {
        this.code = errorCode;
        this.message = message;
        this.status = status;
        this.detailError = detail;
        this.rootCause = cause;
    }
}
