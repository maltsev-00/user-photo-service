package com.user.photo.storage.service.exception;

public class NotFoundFileException extends RuntimeException{
    public NotFoundFileException(String message) {
        super(message);
    }
}
