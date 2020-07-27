package com.jaivardhan.springbootredditclone.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String userName;
    private String email;
    @NotBlank(message = "password in dto is not empty")
    @Size(min = 4,max = 8,message = "password length should be between 4 and 8")
    private String password;
}
