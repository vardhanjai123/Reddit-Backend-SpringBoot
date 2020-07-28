package com.jaivardhan.springbootredditclone.controller;
import com.jaivardhan.springbootredditclone.dto.VoteDto;
import com.jaivardhan.springbootredditclone.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vote")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;


    @PostMapping("/castVote")
    public ResponseEntity<String> castVote(@RequestBody VoteDto voteDto)
    {
           return  ResponseEntity.status(HttpStatus.OK).body(voteService.castVote(voteDto));
    }

}
