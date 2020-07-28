package com.jaivardhan.springbootredditclone.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubRedditDto {

    private Long id;
    private String topicName;
    private String description;
}
