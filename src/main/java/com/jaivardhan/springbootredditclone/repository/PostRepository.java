package com.jaivardhan.springbootredditclone.repository;

import com.jaivardhan.springbootredditclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
