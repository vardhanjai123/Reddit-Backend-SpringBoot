package com.jaivardhan.springbootredditclone.controller;


import com.jaivardhan.springbootredditclone.dto.RegisterRequest;
import com.jaivardhan.springbootredditclone.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest)
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

}
