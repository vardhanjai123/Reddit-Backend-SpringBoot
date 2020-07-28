package com.jaivardhan.springbootredditclone.controller;


import com.jaivardhan.springbootredditclone.dto.CommentDto;
import com.jaivardhan.springbootredditclone.dto.CommentResponseDto;
import com.jaivardhan.springbootredditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

   @GetMapping("/getCommentsByPostId/{id}")
   public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable Long id)
   {
      return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPostId(id));
   }

   @GetMapping("/getCommentsByUsername/{userName}")
   public ResponseEntity<List<CommentResponseDto>> getCommentsByUsername(@PathVariable String userName)
   {
      return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByUsername(userName));
   }

}
