package com.Focus.Reddit.controller;

import com.Focus.Reddit.dto.AuthenticationResponse;
import com.Focus.Reddit.dto.LoginRequest;
import com.Focus.Reddit.dto.RefreshTokenRequest;
import com.Focus.Reddit.dto.RegisterRequset;
import com.Focus.Reddit.service.AuthService;
import com.Focus.Reddit.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;


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
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest)
            throws UsernameNotFoundException {

        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshToken(
            @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws UsernameNotFoundException {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Succcessfully");
    }

}

