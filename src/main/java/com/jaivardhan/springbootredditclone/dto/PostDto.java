package com.jaivardhan.springbootredditclone.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String postTitle;
    private String description;
    private String subRedditName;
    private MultipartFile image;
}
