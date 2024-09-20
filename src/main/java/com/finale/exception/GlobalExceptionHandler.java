package com.finale.exception;

import com.finale.common.ApiResponse;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND).body(ApiResponse.notFoundResponse(exception.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse> duplicateExceptionHandler(IllegalStateException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.badRequestResponse(exception.getMessage()));
    }

    @ExceptionHandler(CoolsmsException.class)
    public ResponseEntity<ApiResponse> smsExceptionHandler(CoolsmsException exception) {
        return ResponseEntity.internalServerError().body(ApiResponse.errorResponse(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> allExceptionHandler(Exception exception) {
        return ResponseEntity.internalServerError().body(ApiResponse.errorResponse(exception.getMessage()));
    }
}
