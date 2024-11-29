package com.kitsuno.exception.rest;

public class FlashcardAlreadyExistsException extends RuntimeException {

    public FlashcardAlreadyExistsException(String message) {
        super(message);
    }

    public FlashcardAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlashcardAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
