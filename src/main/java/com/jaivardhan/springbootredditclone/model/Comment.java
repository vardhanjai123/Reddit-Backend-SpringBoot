package com.jaivardhan.springbootredditclone.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    private String text;

    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserReddit userReddit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;


}


