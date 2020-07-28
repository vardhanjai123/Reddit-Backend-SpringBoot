package com.jaivardhan.springbootredditclone.service;


import com.jaivardhan.springbootredditclone.dto.CommentDto;
import com.jaivardhan.springbootredditclone.dto.CommentResponseDto;
import com.jaivardhan.springbootredditclone.exceptions.SpringRedditException;
import com.jaivardhan.springbootredditclone.model.Comment;
import com.jaivardhan.springbootredditclone.model.NotificationEmail;
import com.jaivardhan.springbootredditclone.model.Post;
import com.jaivardhan.springbootredditclone.repository.CommentRepository;
import com.jaivardhan.springbootredditclone.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

     private final UtilityService utilityService;
     private final PostRepository postRepository;
     private final CommentRepository commentRepository;
     private final MailSendService mailSendService;

    public CommentResponseDto create(CommentDto commentDto) {
           Comment comment=mapDtoToComment(commentDto);
           commentRepository.save(comment);
           String postAuthorEmail=comment.getPost().getUserReddit().getEmail();
           String commentUserName=utilityService.getLoggedInUser().getUserName();
        mailSendService.sendMail(new NotificationEmail("Comment Notification",postAuthorEmail,
               commentUserName+" commented on your post"));

        return mapCommentToDto(comment);
    }

    public Comment mapDtoToComment(CommentDto commentDto)
    {
        Comment comment=new Comment();
        comment.setText(commentDto.getText());
        comment.setUserReddit(utilityService.getLoggedInUser());
        Optional<Post> post=postRepository.findById(commentDto.getPostId());
        post.orElseThrow(()->new SpringRedditException("Post does not exist"));
        comment.setPost(post.get());
        comment.setCreatedAt(Instant.now());
        return comment;
    }

    public CommentResponseDto mapCommentToDto(Comment comment)
    {
        CommentResponseDto commentResponseDto=new CommentResponseDto();
        commentResponseDto.setCreatedAt(comment.getCreatedAt());
        commentResponseDto.setId(comment.getId());
        commentResponseDto.setText(comment.getText());
        commentResponseDto.setUserId(comment.getUserReddit().getUserId());
        commentResponseDto.setPostId(comment.getPost().getId());
        return commentResponseDto;
    }

}
