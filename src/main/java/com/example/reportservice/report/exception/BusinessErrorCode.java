package com.example.reportservice.report.exception;

import com.example.reportservice.report.response.ErrorResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

/**
 * All business error code will be defined here.
 */
public final class BusinessErrorCode {

    private BusinessErrorCode() {
        throw new IllegalStateException("Utility class");
    }

    @UtilityClass
    public static final class ReportErrorCode {

        public static final ErrorResponse GENERATE_PDF_ERROR = new ErrorResponse("000001",
            "Generate pdf error, please check parameters",
            HttpStatus.BAD_REQUEST);
    }
}
