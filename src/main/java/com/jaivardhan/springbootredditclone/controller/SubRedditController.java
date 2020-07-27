package com.jaivardhan.springbootredditclone.controller;


import com.jaivardhan.springbootredditclone.dto.SubRedditDto;
import com.jaivardhan.springbootredditclone.service.SubRedditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subreddit")
@AllArgsConstructor
public class SubRedditController {

    private final SubRedditService subRedditService;

    @GetMapping("/getAll")
    public String getResponse()
    {
        return "Access successful";
    }

    @PostMapping("/create")
    public ResponseEntity<SubRedditDto> createSubReddit(@RequestBody SubRedditDto subRedditDto)
    {
        subRedditService.createSubReddit(subRedditDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(subRedditDto);
    }

    @GetMapping("/getAllSubReddits")
    public ResponseEntity<List<SubRedditDto>> getAllSubReddits()
    {
        List<SubRedditDto> subRedditDtoList=subRedditService.getAllSubReddits();
       return ResponseEntity.status(HttpStatus.CREATED).body(subRedditDtoList);
    }

}
