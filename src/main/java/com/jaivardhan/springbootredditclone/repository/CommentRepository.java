package com.jaivardhan.springbootredditclone.repository;

import com.jaivardhan.springbootredditclone.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
