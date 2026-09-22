package com.hdfc.jwt_control.controller;

import com.hdfc.jwt_control.model.RegisterRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthController {


        @PostMapping("/register")
        public String register(@RequestBody RegisterRequest request){

                return "Registration request received for " + request.getUsername();

        }


}
