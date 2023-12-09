package com.example.galactic_messenger.security;

import com.example.galactic_messenger.Services.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    private final JwtService jwtService;

    public CustomAuthEntryPoint(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        String name = request.getHeader("Authorization");
        if (request.getHeader("Authorization") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

        }
    }
}
