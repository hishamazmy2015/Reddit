package com.Focus.Reddit.service;

import com.Focus.Reddit.dto.AuthenticationResponse;
import com.Focus.Reddit.dto.LoginRequest;
import com.Focus.Reddit.dto.RefreshTokenRequest;
import com.Focus.Reddit.dto.RegisterRequset;
import com.Focus.Reddit.exceptions.SpringRedditException;
import com.Focus.Reddit.model.NotificationEmail;
import com.Focus.Reddit.model.User;
import com.Focus.Reddit.model.VerificationToken;
import com.Focus.Reddit.repository.UserRepository;
import com.Focus.Reddit.repository.VerificationTokenRepository;
import com.Focus.Reddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;
    private final JwtProvider jwtProvider;

    //    @Value("${URL_SITE}")
    private final String URL_SITE = "https://pivotal-industry.herokuapp.com";
//    private final String URL_SITE = "http://localhost:8080";

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final VerificationTokenRepository verificationTokenRepository;

    private final RefreshTokenService refreshTokenService;

    public void signup(@RequestBody RegisterRequset req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");
        System.out.println(" Send the Email successfully ");

        String token = generateVerificationToken(user);
        System.out.println("To this point signup is ok");
        mailService.sendMail(user.getEmail(),
                new NotificationEmail("please Active your Account", user.getEmail(),
                        "Thanks for Sign up please click here to redirect   " +
                                URL_SITE + "/api/auth/accountVerification/" + token));
        System.out.println(" All >>>>>>>>>>>>>>>>> Send the Email successfully "+URL_SITE + "/api/auth/accountVerification/" + token);
        System.out.println(" URL_SITE and verification >>>>>>>>>>>>>>>>> Send the Email successfully "+URL_SITE + "/api/auth/accountVerification/");
        System.out.println(" URL_SITE only >>>>>>>>>>>>>>>>> Send the Email successfully "+URL_SITE );
    }
//    http://localhost:8080
//    URL_SITE

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Name is not existing "));
    }


    public void verification(String token) {
        Optional<VerificationToken> byToken = verificationTokenRepository.findByToken(token);
        byToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(byToken.get());
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new SpringRedditException("User not Found" + username));
        user.setEnabled(true);
        userRepository.save(user);

    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generatedToken(authenticate);

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername()).build();

    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generatedTokenWithUsername(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }
}

