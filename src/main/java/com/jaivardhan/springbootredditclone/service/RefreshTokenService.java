package com.jaivardhan.springbootredditclone.service;

import com.jaivardhan.springbootredditclone.exceptions.SpringRedditException;
import com.jaivardhan.springbootredditclone.model.RefreshToken;
import com.jaivardhan.springbootredditclone.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {
      private final RefreshTokenRepository refreshTokenRepository;
      public RefreshToken generateRefreshToken()
      {
          RefreshToken refreshToken=new RefreshToken();
          refreshToken.setToken(UUID.randomUUID().toString());
          refreshToken.setCreatedAt(Instant.now());
          refreshTokenRepository.save(refreshToken);
          return refreshToken;
      }
      public void validateRefreshToken(String token)
      {
          refreshTokenRepository.findByToken(token).orElseThrow(()->new SpringRedditException("The token does not exist"));
      }
      @Transactional
      public void deleteToken(String token)
      {
          refreshTokenRepository.deleteByToken(token);
      }
}
