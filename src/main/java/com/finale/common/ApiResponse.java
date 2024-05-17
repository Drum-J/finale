package com.finale.common;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

public record ApiResponse(int status,
                          String message,
                          LocalDateTime time,
                          Object data) {

    public static ApiResponse successResponse(Object data) {
        return new ApiResponse(OK.value(), OK.getReasonPhrase(),
                LocalDateTime.now(), data);
    }

    public static ApiResponse notFoundResponse(Object data) {
        return new ApiResponse(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(),
                LocalDateTime.now(), data);
    }

    public static ApiResponse badRequestResponse(Object data) {
        return new ApiResponse(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase(),
                LocalDateTime.now(), data);
    }

    public static ApiResponse errorResponse(Object data) {
        return new ApiResponse(INTERNAL_SERVER_ERROR.value(), "ERROR",
                LocalDateTime.now(), data);
    }
}
