package com.Focus.Reddit.service;

import com.Focus.Reddit.dto.AuthenticationResponse;
import com.Focus.Reddit.dto.LoginRequest;
import com.Focus.Reddit.dto.RegisterRequset;
import com.Focus.Reddit.exceptions.SpringRedditException;
import com.Focus.Reddit.model.NotificationEmail;
import com.Focus.Reddit.model.User;
import com.Focus.Reddit.model.VerificationToken;
import com.Focus.Reddit.repository.UserRepository;
import com.Focus.Reddit.repository.VerificationTokenRepository;
import com.Focus.Reddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;
    private final JwtProvider jwtProvider;


    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final VerificationTokenRepository verificationTokenRepository;

    public void signup(@RequestBody RegisterRequset req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        System.out.println("To this point signup is ok");
        mailService.sendMail(
                new NotificationEmail("please Active your Account", user.getEmail(),
                        "Thanks for Sign up please click here to redirect   +" +
                                "http://localhost:8080/api/auth/accountVerification/" + token));
    }


    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
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
        return  new AuthenticationResponse(token,loginRequest.getUsername());
    }
}

