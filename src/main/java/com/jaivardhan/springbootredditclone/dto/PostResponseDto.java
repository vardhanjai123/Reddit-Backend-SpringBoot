package com.jaivardhan.springbootredditclone.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String postTitle;
    private String description;
    private String subRedditName;
    private String imageUrl;
    private Integer voteCount;
    private Instant createdAt;
}
