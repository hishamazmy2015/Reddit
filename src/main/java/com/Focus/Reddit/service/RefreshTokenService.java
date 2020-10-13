package com.Focus.Reddit.service;

import com.Focus.Reddit.exceptions.SpringRedditException;
import com.Focus.Reddit.model.RefreshToken;
import com.Focus.Reddit.repository.PostRepository;
import com.Focus.Reddit.repository.RefreshTokenRepository;
import com.Focus.Reddit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;


@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        return refreshTokenRepository.save(refreshToken);
    }


    void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringRedditException("Invalid refresh Token"));

    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);

    }


}
