package com.jaivardhan.springbootredditclone.repository;

import com.jaivardhan.springbootredditclone.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    long deleteByToken(String token);
}
