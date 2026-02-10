package com.uriel.task_manager.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseResponse> handleException(Exception exception) {
        BaseResponse response = new BaseResponse();
        response.setStatus(exception.getMessage());
        response.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}