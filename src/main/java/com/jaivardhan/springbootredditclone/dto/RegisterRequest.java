package com.jaivardhan.springbootredditclone.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
     String userName;
    private String email;
    private String password;
}
