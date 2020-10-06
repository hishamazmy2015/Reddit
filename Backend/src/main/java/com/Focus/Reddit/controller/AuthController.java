package com.Focus.Reddit.controller;

import com.Focus.Reddit.dto.AuthenticationResponse;
import com.Focus.Reddit.dto.LoginRequest;
import com.Focus.Reddit.dto.RegisterRequset;
import com.Focus.Reddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequset req) {
        authService.signup(req);
        return new ResponseEntity<>("User Registration Successful ", HttpStatus.OK);
    }


    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verification(token);
        return new ResponseEntity<>("Account Activated Successful ", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) throws UsernameNotFoundException {
        return authService.login(loginRequest);
    }


}

