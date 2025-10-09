package com.example.gatewayserver.exception;


import java.time.LocalDateTime;
public class ErrorResponse {
    private int errorCode;
    private String errorMessage;
    private LocalDateTime errorOccuredAt;
    private String exception;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getErrorOccuredAt() {
        return errorOccuredAt;
    }

    public void setErrorOccuredAt(LocalDateTime errorOccuredAt) {
        this.errorOccuredAt = errorOccuredAt;
    }


    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
