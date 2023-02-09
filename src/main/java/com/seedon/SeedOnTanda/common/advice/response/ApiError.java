package com.seedon.SeedOnTanda.common.advice.response;

import org.springframework.http.HttpStatus;

public record ApiError<T>(
        String timestamp,
        int status,
        HttpStatus error,
        String message,
        String path,
        T data
) {
}
