package com.jaivardhan.springbootredditclone.service;

import com.jaivardhan.springbootredditclone.model.UserReddit;
import com.jaivardhan.springbootredditclone.repository.UserRedditRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRedditRepository userRedditRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserReddit> userReddit=userRedditRepository.findByUserName(s);
        userReddit.orElseThrow(()->new UsernameNotFoundException("User with specified username doesnot exists"));
       // return new User(userReddit.get().getUserName(),userReddit.get().getPassword(),new ArrayList<>());
         return new User(userReddit.get().getUserName(),userReddit.get().getPassword(),userReddit.get().isEnabled(),
                 true,true,true,new ArrayList<>());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
