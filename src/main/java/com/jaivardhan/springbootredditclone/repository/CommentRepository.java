package com.jaivardhan.springbootredditclone.repository;

import com.jaivardhan.springbootredditclone.model.Comment;
import com.jaivardhan.springbootredditclone.model.Post;
import com.jaivardhan.springbootredditclone.model.UserReddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findByUserReddit(UserReddit userReddit);
}
