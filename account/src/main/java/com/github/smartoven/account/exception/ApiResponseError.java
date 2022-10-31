package com.github.smartoven.account.exception;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponseError {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String path;
}
