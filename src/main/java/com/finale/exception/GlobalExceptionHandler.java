package com.finale.exception;

import com.finale.common.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        return ApiResponse.notFoundResponse(exception.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ApiResponse duplicateExceptionHandler(IllegalStateException exception) {
        return ApiResponse.badRequestResponse(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse allExceptionHandler(Exception exception) {
        return ApiResponse.errorResponse(exception.getMessage());
    }
}
