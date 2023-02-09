package com.seedon.SeedOnTanda.common.advice;

import com.seedon.SeedOnTanda.common.advice.response.ApiError;
import com.seedon.SeedOnTanda.common.advice.response.Errors;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        final var errors = new Errors(Objects.requireNonNull(ex.getFieldError()).getField(), errorMessage);
        final var response = createErrorMessage(String.format("%s %s", LocalDate.now(), LocalTime.now()),
                HttpStatus.valueOf(ex.getStatusCode().value()),
                "Validation failed",
                request.getRequestURI(),
                List.of(errors));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    private <T> ApiError<T> createErrorMessage(String timestamp, HttpStatus error, String message, String path, T data) {
        return new ApiError<>(
                timestamp, error.value(), error, message, path, data);
    }
}
