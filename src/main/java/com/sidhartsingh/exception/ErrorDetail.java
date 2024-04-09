package com.sidhartsingh.exception;

import java.time.LocalDateTime;

public class ErrorDetail {
    private String error;
    private String detail;
    private LocalDateTime timeStamp;

    public ErrorDetail(String error, String detail, LocalDateTime timeStamp) {
        this.error = error;
        this.detail = detail;
        this.timeStamp = timeStamp;
    }
}
