package com.jaivardhan.springbootredditclone.repository;

import com.jaivardhan.springbootredditclone.model.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubRedditRepository extends JpaRepository<SubReddit,Long> {
}
