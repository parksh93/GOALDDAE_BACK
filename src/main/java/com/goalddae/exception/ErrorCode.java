package com.goalddae.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_MATCH_CONDITIONS(HttpStatus.BAD_REQUEST, "notMatchConditions"),
    OVER_PLAYER_NUM_MATCH(HttpStatus.LOCKED, "overPlayer");

    private final HttpStatus httpStatus;
    private final String detail;
}
