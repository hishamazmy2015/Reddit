package com.Focus.Reddit.security;

import com.Focus.Reddit.exceptions.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.sql.Date;
import java.time.Instant;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @PostConstruct
    public void init() {
        try {
            keyStore = keyStore.getInstance("JKS");

            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");

            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException e) {
            throw new SpringRedditException("exception occured while loading keyStore");
        }
    }


    public String generatedToken(Authentication auth) {
        User principal = (User) auth.getPrincipal();

        return Jwts.builder().setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    public String generatedTokenWithUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    private PrivateKey getPrivateKey() {

        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());

        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException e) {
            throw new SpringRedditException("exception occured while loading keyStore");
        }
    }

    public boolean validateToken(String jwt) {
        Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }


    public PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringRedditException("Exception occured while retrieving public");
        }

    }

    public String getUsernameFromJwt(String token) {
        Claims body = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();
        return body.getSubject();

    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }

/**
 *
 *
 *
 *
 *
 *
 * */
}
