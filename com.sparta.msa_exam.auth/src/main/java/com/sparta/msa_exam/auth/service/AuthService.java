package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.dto.SignUpResponse;
import com.sparta.msa_exam.auth.dto.SignUpRequest;
import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public SignUpResponse signUp(SignUpRequest request) {
        User user = new User();
        user.setUserId(request.userId());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");
        userRepository.save(user);
        return new SignUpResponse("회원가입 성공");
    }

    public String signIn(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID or password"));
        if (!passwordEncoder.matches("1234", user.getPassword())) {
            throw new IllegalArgumentException("Invalid user ID or password");
        }
        return tokenService.createAccessToken(user.getUserId(), user.getRole());
    }

}
