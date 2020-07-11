package com.jaivardhan.springbootredditclone.service;


import com.jaivardhan.springbootredditclone.dto.RegisterRequest;
import com.jaivardhan.springbootredditclone.model.UserReddit;
import com.jaivardhan.springbootredditclone.repository.UserRedditRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRedditRepository userRedditRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(RegisterRequest registerRequest)
    {
        if(userRedditRepository==null)
            System.out.println("********userRedditRepository is null**********");
        UserReddit userReddit=new UserReddit();

        userReddit.setUserName(registerRequest.getUserName());
        userReddit.setEmail(registerRequest.getEmail());
        userReddit.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userReddit.setEnabled(false);
        userReddit.setCreatedAt(Instant.now());
        userRedditRepository.save(userReddit);

    }
}
