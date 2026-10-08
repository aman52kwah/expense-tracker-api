package com.example.expensetracker.controller;

import com.example.expensetracker.dto.*;
import com.example.expensetracker.model.User;
import com.example.expensetracker.security.JwtAuthFilter;
import com.example.expensetracker.service.AuthService;
import com.example.expensetracker.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){
        User user = authService.register(request);
        RegisterResponse response = new RegisterResponse(
                "Reqistration successful. Please very your email.",
                user.getEmail()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@Valid @RequestBody VerifyRequest request){
        authService.verifyEmail(request.getToken());
        return ResponseEntity.ok("Email verified successfully");
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        User user = authService.login(request);
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
