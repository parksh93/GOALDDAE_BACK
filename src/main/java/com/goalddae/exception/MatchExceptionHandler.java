package com.goalddae.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class MatchExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = NotMatchConditionsException.class)
    protected ResponseEntity<ErrorResponse> notMatchConditionException(NotMatchConditionsException e){
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
    @ExceptionHandler(value = OverPlayerNumMatchException.class)
    protected ResponseEntity<ErrorResponse> overPlayerNumMatchException(OverPlayerNumMatchException e){
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
