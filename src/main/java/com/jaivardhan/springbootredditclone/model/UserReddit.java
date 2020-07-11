package com.jaivardhan.springbootredditclone.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class UserReddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Usename can't be empty")
    private String userName;

    @NotBlank(message = "Password is Required")
    private String password;

    @Email
    @NotBlank(message = "Email cannot be empty")
    private String email;

    private Instant createdAt;

    private boolean enabled;


}
