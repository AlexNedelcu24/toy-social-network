package com.example.retea_gui.domain.validators;

public class ValidationException extends RuntimeException{
    public ValidationException(){}

    public ValidationException(String message){
        super(message);
    }

    public ValidationException(Throwable cause){
        super(cause);
    }
}
