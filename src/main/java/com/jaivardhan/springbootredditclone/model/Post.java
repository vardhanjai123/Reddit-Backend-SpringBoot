package com.jaivardhan.springbootredditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    private String imageUrl;

    @NotBlank(message = "Post Title cannot be blank")
    private String postTitle;

    @Nullable
    @Lob
    private String description;

    private Integer voteCount;

    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY) //we will have a unidirectional relationship
    private UserReddit userReddit;

    @ManyToOne(fetch = FetchType.LAZY) //we will have a unidirectional relationship
    private SubReddit subReddit;

}
