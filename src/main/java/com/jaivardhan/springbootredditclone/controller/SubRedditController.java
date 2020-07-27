package com.jaivardhan.springbootredditclone.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subreddit")
@AllArgsConstructor
public class SubRedditController {

    @GetMapping("/getAll")
    public String getResponse()
    {
        return "Access successful";
    }

}
