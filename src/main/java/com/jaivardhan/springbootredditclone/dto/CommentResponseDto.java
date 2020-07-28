package com.jaivardhan.springbootredditclone.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String text;
    private Instant createdAt;
    private Long userId;
    private Long postId;
}
