package com.sparta.msa_exam.auth.controller;

import com.sparta.msa_exam.auth.dto.SignInResponse;
import com.sparta.msa_exam.auth.dto.SignUpRequest;
import com.sparta.msa_exam.auth.dto.SignUpResponse;
import com.sparta.msa_exam.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @Value("${server.port}")
    private String serverPort;

    private final AuthService authService;

    @PostMapping("/auth/signUp")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest request) {
        SignUpResponse response = authService.signUp(request);
        return ResponseEntity.ok()
                .header("Server-Port", serverPort)
                .body(response);
    }

    @GetMapping("/auth/signIn")
    public ResponseEntity<SignInResponse> createAuthenticationToken(@RequestParam("user_id") String userId) {
        String token = authService.signIn(userId);
        return ResponseEntity.ok()
                .header("Server-Port", serverPort)
                .body(new SignInResponse(token));
    }

}

