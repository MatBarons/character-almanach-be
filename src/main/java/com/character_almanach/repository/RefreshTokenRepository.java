package com.character_almanach.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.character_almanach.model.user.RefreshToken;
import com.character_almanach.model.user.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}
