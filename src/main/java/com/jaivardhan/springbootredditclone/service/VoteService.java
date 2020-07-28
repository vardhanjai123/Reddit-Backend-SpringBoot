package com.jaivardhan.springbootredditclone.service;


import com.jaivardhan.springbootredditclone.dto.VoteDto;
import com.jaivardhan.springbootredditclone.exceptions.SpringRedditException;
import com.jaivardhan.springbootredditclone.model.Post;
import com.jaivardhan.springbootredditclone.model.Vote;
import com.jaivardhan.springbootredditclone.model.VoteType;
import com.jaivardhan.springbootredditclone.repository.PostRepository;
import com.jaivardhan.springbootredditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.jaivardhan.springbootredditclone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final UtilityService utilityService;


    public String castVote(VoteDto voteDto) {

        Optional<Post> post=postRepository.findById(voteDto.getPostId());
        post.orElseThrow(()->new SpringRedditException("Post does not exists"));
        Optional<Vote> recentVote=voteRepository.findTopByPostAndUserRedditOrderByIdDesc(post.get(),utilityService.getLoggedInUser());
        if(recentVote.isPresent()&&voteDto.getVoteType().equals(recentVote.get().getVoteType()))
            throw new SpringRedditException("You cannot "+recentVote.get().getVoteType().toString()
            +" an already "+recentVote.get().getVoteType().toString()+"d post");
        if(UPVOTE.equals(voteDto.getVoteType()))
            post.get().setVoteCount(post.get().getVoteCount()+1);
        else
            post.get().setVoteCount(post.get().getVoteCount()-1);
        Vote vote=mapDtoToVote(voteDto,post.get());
        voteRepository.save(vote);
        return "Successfully "+voteDto.getVoteType().toString();
    }

    private Vote mapDtoToVote(VoteDto voteDto, Post post) {
        Vote vote=new Vote();
        vote.setVoteType(voteDto.getVoteType());
        vote.setUserReddit(utilityService.getLoggedInUser());
        vote.setPost(post);
        return vote;
    }
}
