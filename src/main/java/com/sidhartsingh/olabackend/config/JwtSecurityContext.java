package com.sidhartsingh.olabackend.config;

import org.springframework.stereotype.Component;

@Component
public class JwtSecurityContext {
    public static final String JWT_KEY="eyJhbGciOiJIUzI1NiJ9eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZS2YqV0RLFQyzJgimGPSAFNJFeXYS8";
    public static final String JWT_HEADER="Authorization";
}
