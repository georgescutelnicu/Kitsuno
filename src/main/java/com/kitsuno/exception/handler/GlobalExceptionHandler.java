package com.kitsuno.exception.handler;

import com.kitsuno.exception.ErrorResponse;
import com.kitsuno.utils.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleGenericException(RuntimeException exc) {

        String timeStamp = DateUtils.formatTimestamp(System.currentTimeMillis());

        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("An internal error occurred");
        error.setTimeStamp(timeStamp);
        error.setDetails("Our team has been notified. " +
                "Try again in a few moments and let us know if the issue continues.");

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", error.getStatus());
        modelAndView.addObject("errorMessage", error.getMessage());
        modelAndView.addObject("errorDetails", error.getDetails());
        modelAndView.addObject("timestamp", error.getTimeStamp());

        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public Object handleNoHandlerFound(Exception ex, HttpServletRequest request) {

        String timeStamp = DateUtils.formatTimestamp(System.currentTimeMillis());

        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage("Resource not found");
        error.setTimeStamp(timeStamp);
        error.setDetails("Check the URL for typos or missing paths.");

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/api")) {
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", error.getStatus());
        modelAndView.addObject("errorMessage", error.getMessage());
        modelAndView.addObject("errorDetails", error.getDetails());
        modelAndView.addObject("timestamp", timeStamp);

        return modelAndView;
    }
}
