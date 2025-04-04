package com.kitsuno.exception.rest;

public class FlashcardNotFoundException extends RuntimeException {

    public FlashcardNotFoundException(String message) {
        super(message);
    }

    public FlashcardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlashcardNotFoundException(Throwable cause) {
        super(cause);
    }
}
