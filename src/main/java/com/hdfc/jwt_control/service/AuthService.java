package com.hdfc.jwt_control.service;

import com.hdfc.jwt_control.model.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public String register(RegisterRequest request) {
        return "Registration done through Service Layer " + request.getUsername();
        // this is business logic
    }
}
