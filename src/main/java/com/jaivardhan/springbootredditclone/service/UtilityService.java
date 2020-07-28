package com.jaivardhan.springbootredditclone.service;


import com.jaivardhan.springbootredditclone.exceptions.SpringRedditException;
import com.jaivardhan.springbootredditclone.model.UserReddit;
import com.jaivardhan.springbootredditclone.repository.UserRedditRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UtilityService {


    private final UserRedditRepository userRedditRepository;

    public UserReddit getLoggedInUser()
    {
        UserDetails user= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username=user.getUsername();
        Optional<UserReddit> userReddit=userRedditRepository.findByUserName(username);
        userReddit.orElseThrow(()->new SpringRedditException("User doesnot exist"));
        return userReddit.get();
    }
}
