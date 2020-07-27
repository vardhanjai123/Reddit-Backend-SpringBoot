package com.jaivardhan.springbootredditclone.repository;

import com.jaivardhan.springbootredditclone.model.SubReddit;
import com.jaivardhan.springbootredditclone.model.UserReddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubRedditRepository extends JpaRepository<SubReddit,Long> {
    List<SubReddit> findAllByUserReddit(UserReddit userReddit);
}
