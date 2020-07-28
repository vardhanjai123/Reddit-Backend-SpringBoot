package com.jaivardhan.springbootredditclone.service;


import com.jaivardhan.springbootredditclone.dto.CommentDto;
import com.jaivardhan.springbootredditclone.dto.CommentResponseDto;
import com.jaivardhan.springbootredditclone.exceptions.SpringRedditException;
import com.jaivardhan.springbootredditclone.model.Comment;
import com.jaivardhan.springbootredditclone.model.NotificationEmail;
import com.jaivardhan.springbootredditclone.model.Post;
import com.jaivardhan.springbootredditclone.model.UserReddit;
import com.jaivardhan.springbootredditclone.repository.CommentRepository;
import com.jaivardhan.springbootredditclone.repository.PostRepository;
import com.jaivardhan.springbootredditclone.repository.UserRedditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

     private final UtilityService utilityService;
     private final PostRepository postRepository;
     private final CommentRepository commentRepository;
     private final MailSendService mailSendService;
     private final UserRedditRepository userRedditRepository;

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

    public List<CommentResponseDto> getCommentsByPostId(Long id) {
        Optional<Post> post=postRepository.findById(id);
        post.orElseThrow(()->new SpringRedditException("No post with this id exist"));
        List<Comment> comments=commentRepository.findByPost(post.get());
        return comments.stream().map(this::mapCommentToDto).collect(Collectors.toList());
    }

    public List<CommentResponseDto> getCommentsByUsername(String userName) {
        Optional<UserReddit> userReddit=userRedditRepository.findByUserName(userName);
        userReddit.orElseThrow(()->new SpringRedditException("User with this username does not exist"));
        List<Comment> comments=commentRepository.findByUserReddit(userReddit.get());
        return comments.stream().map(this::mapCommentToDto).collect(Collectors.toList());
    }
}
