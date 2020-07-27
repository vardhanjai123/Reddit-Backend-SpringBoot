package com.jaivardhan.springbootredditclone.controller;


import com.jaivardhan.springbootredditclone.dto.AuthenticationResponse;
import com.jaivardhan.springbootredditclone.dto.LoginRequest;
import com.jaivardhan.springbootredditclone.dto.RegisterRequest;
import com.jaivardhan.springbootredditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest)
    {
            if(authService==null)
                System.out.println("*****************Auth Service is null***********8");
            authService.registerUser(registerRequest);
            return new ResponseEntity<>("User Registration Successful",HttpStatus.OK);
    }

    @GetMapping("/verificationToken/{token}")
    public ResponseEntity<String> verifyToken(@PathVariable String token)
    {
        authService.verifyToken(token);
        return new ResponseEntity<>("Account Activated",HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest)
    {
        return authService.login(loginRequest);
    }

}
