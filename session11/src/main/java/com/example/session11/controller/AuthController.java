package com.example.session11.controller;

import com.example.session11.dto.UserRegisterDTO;
import com.example.session11.dto.request.LoginRequest;
import com.example.session11.dto.request.VerifyOtpRequest;
import com.example.session11.dto.response.UserRegisterResponse;
import com.example.session11.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO){
        return new ResponseEntity<>(userService.register(userRegisterDTO), HttpStatus.OK);
    }

    @PostMapping("/active-user")
    public ResponseEntity<String> activeUser(@RequestBody VerifyOtpRequest request) {
        String message = userService.verifyAccount(request);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

}
