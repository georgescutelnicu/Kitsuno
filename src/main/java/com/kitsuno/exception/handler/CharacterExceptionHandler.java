package com.kitsuno.exception.handler;

import com.kitsuno.exception.ErrorResponse;
import com.kitsuno.exception.rest.CharacterNotFoundException;
import com.kitsuno.utils.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CharacterExceptionHandler {

    @ExceptionHandler(CharacterNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCharacterNotFoundException(CharacterNotFoundException exc) {

        String timeStamp = DateUtils.formatTimestamp(System.currentTimeMillis());

        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(timeStamp);
        error.setDetails("The specified character could not be found in the database.");

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
