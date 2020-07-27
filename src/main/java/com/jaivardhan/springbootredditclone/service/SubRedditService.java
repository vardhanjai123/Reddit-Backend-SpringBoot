package com.jaivardhan.springbootredditclone.service;
import com.jaivardhan.springbootredditclone.dto.SubRedditDto;
import com.jaivardhan.springbootredditclone.exceptions.SpringRedditException;
import com.jaivardhan.springbootredditclone.model.SubReddit;
import com.jaivardhan.springbootredditclone.model.UserReddit;
import com.jaivardhan.springbootredditclone.repository.SubRedditRepository;
import com.jaivardhan.springbootredditclone.repository.UserRedditRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private final UtilityService utilityService;

    public void createSubReddit(SubRedditDto subRedditDto) {
        SubReddit subReddit=mapSubReddit(subRedditDto);
        subRedditDto.setId(subRedditRepository.save(subReddit).getId());
    }

    private SubReddit mapSubReddit(SubRedditDto subRedditDto) {
          SubReddit subReddit=new SubReddit();
          subReddit.setTopicName(subRedditDto.getTopicName());
          subReddit.setDescription(subRedditDto.getDescription());
          subReddit.setUserReddit(utilityService.getLoggedInUser());
          subReddit.setCreatedAt(Instant.now());
          return subReddit;
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

    public SubRedditDto getSubRedditById(Long id) {
        Optional<SubReddit> subReddit=subRedditRepository.findById(id);
        subReddit.orElseThrow(()->new SpringRedditException("SubReddit with this Id does not exists"));
        return mapSubRedditDto(subReddit.get());
    }

    public List<SubRedditDto> getSubRedditByUsername(String username) {
        Optional<UserReddit> userReddit=userRedditRepository.findByUserName(username);
        userReddit.orElseThrow(()->new SpringRedditException("User with "+username+" does not exist"));
        List<SubReddit> subRedditList=subRedditRepository.findAllByUserReddit(userReddit.get());
        return subRedditList.stream().map(this::mapSubRedditDto).collect(Collectors.toList());
    }
}
