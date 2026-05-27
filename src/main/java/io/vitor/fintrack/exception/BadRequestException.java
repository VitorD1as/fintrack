package io.vitor.fintrack.exception;

public class BadRequestException extends Exception{
    public BadRequestException(String message) {
        super(message);
    }
}