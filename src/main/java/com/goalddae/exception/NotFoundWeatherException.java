package com.goalddae.exception;

public class NotFoundWeatherException extends RuntimeException{
    public NotFoundWeatherException(String message){
        super(message);
    }
}
