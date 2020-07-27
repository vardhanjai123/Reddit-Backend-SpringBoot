package com.jaivardhan.springbootredditclone.service;

import com.jaivardhan.springbootredditclone.dto.SubRedditDto;
import com.jaivardhan.springbootredditclone.exceptions.SpringRedditException;
import com.jaivardhan.springbootredditclone.model.SubReddit;
import com.jaivardhan.springbootredditclone.model.UserReddit;
import com.jaivardhan.springbootredditclone.repository.SubRedditRepository;
import com.jaivardhan.springbootredditclone.repository.UserRedditRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Builder
public class SubRedditService {

    private final UserRedditRepository userRedditRepository;
    private final SubRedditRepository subRedditRepository;

    public void createSubReddit(SubRedditDto subRedditDto) {
        SubReddit subReddit=mapSubReddit(subRedditDto);
        subRedditDto.setId(subRedditRepository.save(subReddit).getId());
    }

    private SubReddit mapSubReddit(SubRedditDto subRedditDto) {
          SubReddit subReddit=new SubReddit();
          subReddit.setTopicName(subRedditDto.getTopicName());
          subReddit.setDescription(subRedditDto.getDescription());
          subReddit.setUserReddit(getLoggedInUser());
          subReddit.setCreatedAt(Instant.now());
          return subReddit;
    }
    private UserReddit getLoggedInUser()
    {
        UserDetails user= (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username=user.getUsername();
        Optional<UserReddit> userReddit=userRedditRepository.findByUserName(username);
        userReddit.orElseThrow(()->new SpringRedditException("User doesnot exist"));
        return userReddit.get();
    }

    public List<SubRedditDto> getAllSubReddits() {
        List<SubReddit> subRedditList=subRedditRepository.findAll();
        List<SubRedditDto> subRedditDtoList=subRedditList.stream()
                .map(this::mapSubRedditDto).collect(Collectors.toList());
        return subRedditDtoList;

    }

    private <R> SubRedditDto mapSubRedditDto(SubReddit subReddit) {
        SubRedditDto subRedditDto=new SubRedditDto();
        subRedditDto.setTopicName(subReddit.getTopicName());
        subRedditDto.setDescription(subReddit.getDescription());
        subRedditDto.setId(subReddit.getId());
        return subRedditDto;
    }

}
