package com.programming1.developerHt.exception;

import com.programming1.developerHt.dto.DeveloperErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

//각 controller에 advice를 해줌
@RestControllerAdvice
@Slf4j
public class DeveloperExceptionHandler {
    @ExceptionHandler(DeveloperException.class)
    public DeveloperErrorResponse handleException(DeveloperException e, HttpServletRequest request) {
        log.error("errorCode: {}, url: {}, message: {}",
                e.getDeveloperErrorCode(), request.getRequestURI(), e.getDetailMessage());

        return DeveloperErrorResponse.builder()
                .errorCode(e.getDeveloperErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class, //ex) put 요청에 해당되는 url 값이 post 요청을 하게 될 경우
            MethodArgumentNotValidException.class
    })
    public DeveloperErrorResponse handleBadRequest(Exception e,HttpServletRequest request){
        log.error("url: {}, message: {}", request.getRequestURI(), e.getMessage());

        return DeveloperErrorResponse.builder()
                .errorCode(DeveloperErrorCode.INVALID_REQUEST)
                .errorMessage(DeveloperErrorCode.INVALID_REQUEST.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public DeveloperErrorResponse handleException(Exception e,HttpServletRequest request){
        log.error("url: {}, message: {}", request.getRequestURI(), e.getMessage());

        return DeveloperErrorResponse.builder()
                .errorCode(DeveloperErrorCode.INTERNER_SERVER_ERROR)
                .errorMessage(DeveloperErrorCode.INTERNER_SERVER_ERROR.getMessage())
                .build();
    }

}
