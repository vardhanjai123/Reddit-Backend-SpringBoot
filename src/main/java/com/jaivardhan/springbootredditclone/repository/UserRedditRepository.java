package com.jaivardhan.springbootredditclone.repository;


import com.jaivardhan.springbootredditclone.model.UserReddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRedditRepository extends JpaRepository<UserReddit,Long> {
}
