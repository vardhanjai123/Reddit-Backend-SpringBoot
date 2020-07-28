package com.jaivardhan.springbootredditclone.controller;

import com.jaivardhan.springbootredditclone.dto.PostDto;
import com.jaivardhan.springbootredditclone.dto.PostResponseDto;
import com.jaivardhan.springbootredditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(value = "/create",consumes = {"multipart/form-data"})
    public ResponseEntity<PostResponseDto> create(@ModelAttribute PostDto postDto) throws IOException {
         return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(postDto));
    }


}