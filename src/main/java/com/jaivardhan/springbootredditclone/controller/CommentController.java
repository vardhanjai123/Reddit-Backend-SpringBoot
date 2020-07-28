package com.jaivardhan.springbootredditclone.controller;


import com.jaivardhan.springbootredditclone.dto.CommentDto;
import com.jaivardhan.springbootredditclone.dto.CommentResponseDto;
import com.jaivardhan.springbootredditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/comment")
@AllArgsConstructor
public class CommentController {
   private final CommentService commentService;

   @PostMapping("/create")
   public ResponseEntity<CommentResponseDto> create(@RequestBody CommentDto commentDto)
   {
      return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(commentDto));
   }

}
