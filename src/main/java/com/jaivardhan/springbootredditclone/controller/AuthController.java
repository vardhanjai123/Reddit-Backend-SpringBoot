package com.jaivardhan.springbootredditclone.controller;


import com.jaivardhan.springbootredditclone.dto.RegisterRequest;
import com.jaivardhan.springbootredditclone.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@NoArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest registerRequest)
    {
        if(authService==null)
            System.out.println("*****************Auth Service is null***********8");
            authService.registerUser(registerRequest);
    }

}
