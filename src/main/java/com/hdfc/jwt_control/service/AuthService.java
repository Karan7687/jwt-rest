package com.hdfc.jwt_control.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public String register(String username) {
        return "Registration done through Service Layer " + username;
        // this is business logic
    }
}
