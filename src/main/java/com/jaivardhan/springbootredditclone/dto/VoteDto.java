package com.jaivardhan.springbootredditclone.dto;


import com.jaivardhan.springbootredditclone.model.VoteType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoteDto {
    private VoteType voteType;
    private Long postId;
}
