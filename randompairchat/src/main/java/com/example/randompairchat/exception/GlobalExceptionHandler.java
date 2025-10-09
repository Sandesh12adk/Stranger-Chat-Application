package com.example.randompairchat.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlExtra(Exception ex, HttpServletRequest request){
        ErrorResponse errorResponse= new ErrorResponse();
        errorResponse.setErrorCode(500);
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setException(ex.getClass().getName());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());
        errorResponse.setErrorOccuredAt(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> firstHandler(ResourceNotFoundException ex, HttpServletRequest request){
        ErrorResponse errorResponse= new ErrorResponse();
        errorResponse.setErrorCode(404);
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setException(ex.getClass().getName());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());
        errorResponse.setErrorOccuredAt(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }
}
