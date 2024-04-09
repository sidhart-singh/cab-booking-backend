package com.sidhartsingh.olabackend.response;

import com.sidhartsingh.olabackend.domain.UserRole;

public class JwtResponse {
    private String jwt;
    private String message;
    private Boolean isAuthenticated;
    private Boolean isError;
    private String errorDetails;
    private UserRole type;

    public JwtResponse() {
    }

    public JwtResponse(String jwt, String message, Boolean isAuthenticated, Boolean isError, String errorDetails, UserRole type) {
        this.jwt = jwt;
        this.message = message;
        this.isAuthenticated = isAuthenticated;
        this.isError = isError;
        this.errorDetails = errorDetails;
        this.type = type;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public UserRole getType() {
        return type;
    }

    public void setType(UserRole type) {
        this.type = type;
    }
}
