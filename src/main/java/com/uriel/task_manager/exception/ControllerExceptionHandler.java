package com.uriel.task_manager.exception;

import com.uriel.task_manager.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BadCredentialsException exception) {
        return buildErrorResponse(exception, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception exception, HttpStatus status) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(exception.getMessage());
        response.setStatus(String.valueOf(status));
        response.setStackTrace(Arrays.toString(exception.getStackTrace()));
        response.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(response, status);
    }

}