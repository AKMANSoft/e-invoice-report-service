package com.example.reportservice.report.controller;

import com.example.reportservice.report.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleCityNotFoundException(
            ApplicationException ex) {
        var body = bodyRes(ex.getCode(),ex.getMessage(),ex.getStatus());
        return new ResponseEntity<>(body, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleBaseException(
            Exception ex) {
        var body = bodyRes(null,ex.getMessage(),null);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> bodyRes(String code, String errorMessage, HttpStatus status){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", code);
        body.put("errorMessage", errorMessage);
        body.put("httpStatus", status);
        return body;
    }
}
