package com.jaivardhan.springbootredditclone.repository;


import com.jaivardhan.springbootredditclone.model.UserReddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRedditRepository extends JpaRepository<UserReddit,Long> {
    Optional<UserReddit> findByUserName(String name);
}
