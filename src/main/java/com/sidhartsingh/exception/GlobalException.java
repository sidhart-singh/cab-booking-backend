package com.sidhartsingh.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> userExceptionHandler(UserException userException, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(userException.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DriverException.class)
    public ResponseEntity<ErrorDetail> driverExceptionHandler(DriverException driverException, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(driverException.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RideException.class)
    public ResponseEntity<ErrorDetail> userExceptionHandler(RideException rideException, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(rideException.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetail> handleViolationException(ConstraintViolationException exception) {
        StringBuilder errorMessage = new StringBuilder();
        for(ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            errorMessage.append(violation.getMessage() + "\n");
        }
        ErrorDetail errorDetail = new ErrorDetail(errorMessage.toString(), "Validation Exception", LocalDateTime.now());

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        ErrorDetail errorDetail = new ErrorDetail(exception.getBindingResult().getFieldError().getDefaultMessage(), "Validation Exception", LocalDateTime.now());

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail>  otherExceptionHandler(Exception exception, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(exception.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }
}
