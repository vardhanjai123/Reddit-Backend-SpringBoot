package com.jaivardhan.springbootredditclone.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDto {
    private String text;
    private Long postId;
}
