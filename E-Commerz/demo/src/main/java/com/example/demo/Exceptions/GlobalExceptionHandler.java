package com.example.demo.Exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler{

    @ExceptionHandler(Exception.class)
    @ResponseStatus
    public String unexpected(Exception e, WebRequest request){
        String userMessage = request.getDescription(false);
        log.error("Unexpected: " + userMessage, e);
        return userMessage;
    }

    @ExceptionHandler(AssetException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleExceptions(AssetException exception, HttpServletRequest request){
        //Temporal?
        int slash = request.getRequestURI().lastIndexOf("/");
        String param = request.getRequestURI().substring(slash+1);
        String userMessage = (exception.getErrorCode() + " " + exception.getMessage() + ": " + param);
        log.error("Exception: " + userMessage, exception);
        return userMessage;
    }
}
