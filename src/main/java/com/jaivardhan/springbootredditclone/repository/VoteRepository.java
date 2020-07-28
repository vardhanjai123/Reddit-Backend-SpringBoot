package com.jaivardhan.springbootredditclone.repository;

import com.jaivardhan.springbootredditclone.model.Post;
import com.jaivardhan.springbootredditclone.model.UserReddit;
import com.jaivardhan.springbootredditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findTopByPostAndUserRedditOrderByIdDesc(Post post, UserReddit userReddit);
}
