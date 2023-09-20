package com.goalddae.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@AllArgsConstructor
public class NotMatchConditionsException extends RuntimeException{
    private final ErrorCode errorCode;
}
