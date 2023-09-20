package com.goalddae.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OverPlayerNumMatchException extends RuntimeException{
    private final ErrorCode errorCode;
}
