package com.jaivardhan.springbootredditclone.repository;

import com.jaivardhan.springbootredditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote,Long> {
}
