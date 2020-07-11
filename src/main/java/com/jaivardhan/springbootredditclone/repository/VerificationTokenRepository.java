package com.jaivardhan.springbootredditclone.repository;

import com.jaivardhan.springbootredditclone.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
}
