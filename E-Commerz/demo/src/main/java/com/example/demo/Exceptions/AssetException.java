package com.example.demo.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

@Getter
public class AssetException extends RuntimeException{

    public enum ErrorCode{
        General,
        CREDENTIALSERROR,
        USERNAME_DUPLICATE,
        USERNOTFOUND,
        PRODUCTNOTFOUND,
        ORDERNOTFOUND,
        MULTIPLE_FORMAT_ERRORS,
        JSON_ERROR
    }

    private final ErrorCode errorCode;
    private String description;
    private HttpStatus httpStatus;

    public AssetException(Exception e){
        this.description = e.getMessage();
        this.errorCode = ErrorCode.General;
    }
    public AssetException(String mssg, ErrorCode errorCode, HttpStatus httpStatus){
        super(mssg);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
    public AssetException(String mssg, Exception cause){
        super(mssg);
        this.description = cause.getMessage();
        this.errorCode = ErrorCode.General;
    }
}
