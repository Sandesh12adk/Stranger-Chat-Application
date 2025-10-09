package com.example.userservice.exception;

import com.example.userservice.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> firstHandler(UserNotFoundException ex, HttpServletRequest request){
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
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(Exception ex, HttpServletRequest request){
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle2(MethodArgumentNotValidException ex){
        Map<String,String> error= new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((errorObj)->{
            error.put(errorObj.getField(),errorObj.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
