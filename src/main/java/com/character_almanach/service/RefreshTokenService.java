package com.character_almanach.service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.character_almanach.mappers.RefreshTokenMapper;
import com.character_almanach.model.user.RefreshToken;
import com.character_almanach.model.user.User;
import com.character_almanach.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository repository;

    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(7);

    @Transactional
    public String createRefreshToken(User user) {
        repository.deleteByUser(user);
        repository.flush(); 

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plus(REFRESH_TOKEN_TTL));

        return RefreshTokenMapper.toDto(repository.save(token));
    }

    public RefreshToken verifyAndGet(String token) throws RuntimeException{
        RefreshToken refreshToken = repository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            repository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }
}
