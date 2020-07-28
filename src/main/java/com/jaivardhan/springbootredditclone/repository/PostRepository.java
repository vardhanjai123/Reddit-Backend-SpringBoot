package com.jaivardhan.springbootredditclone.repository;

import com.jaivardhan.springbootredditclone.model.Post;
import com.jaivardhan.springbootredditclone.model.SubReddit;
import com.jaivardhan.springbootredditclone.model.UserReddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllBySubReddit(SubReddit subReddit);
    List<Post> findAllByUserReddit(UserReddit userReddit);
}
