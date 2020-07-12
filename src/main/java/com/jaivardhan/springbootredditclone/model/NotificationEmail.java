package com.jaivardhan.springbootredditclone.model;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class NotificationEmail {


    private String subject;

    @Email(message = "Recipient must be a valid email")
    @NotBlank(message = "Recipient cannot be empty")
    private String recipient;

    private String body;

}
