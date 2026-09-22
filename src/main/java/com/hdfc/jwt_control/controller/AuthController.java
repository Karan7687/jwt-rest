package com.hdfc.jwt_control.controller;

import com.hdfc.jwt_control.model.RegisterRequest;
import com.hdfc.jwt_control.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    //"Take the service object provided to me and store it inside my controller."

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {

        return ResponseEntity
                .status(201)
                .body(authService.register(request.getUsername()));

    }
}