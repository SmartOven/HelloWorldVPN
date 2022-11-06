package com.github.smartoven.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseCreator {
    public static <T> ResponseEntity<?> httpOk(T body) {
        return ResponseEntity.ok(body);
    }

    public static ResponseEntity<?> httpBadRequest(String error, String path) {
        return ResponseEntity.badRequest()
                .body(buildError(HttpStatus.BAD_REQUEST, error, path));
    }

    public static ResponseEntity<?> httpNotFound(String error, String path) {
        return new ResponseEntity<>(buildError(HttpStatus.NOT_FOUND, error, path), HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> httpInternalServerError(Throwable throwable, String path) {
        return ResponseEntity.internalServerError()
                .body(buildError(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage(), path));
    }

    private static ApiResponseError buildError(HttpStatus status, String error, String path) {
        return new ApiResponseError(
                LocalDateTime.now(),
                status.value(),
                error,
                path
        );
    }
}
