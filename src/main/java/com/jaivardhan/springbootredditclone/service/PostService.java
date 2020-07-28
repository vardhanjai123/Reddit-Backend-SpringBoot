package com.jaivardhan.springbootredditclone.service;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.jaivardhan.springbootredditclone.dto.PostDto;
import com.jaivardhan.springbootredditclone.dto.PostResponseDto;
import com.jaivardhan.springbootredditclone.exceptions.SpringRedditException;
import com.jaivardhan.springbootredditclone.model.Post;
import com.jaivardhan.springbootredditclone.model.SubReddit;
import com.jaivardhan.springbootredditclone.repository.PostRepository;
import com.jaivardhan.springbootredditclone.repository.SubRedditRepository;
import com.jaivardhan.springbootredditclone.repository.UserRedditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final AmazonS3Client amazonS3Client;
    private final SubRedditRepository subRedditRepository;
    private final UtilityService utilityService;
    private final PostRepository postRepository;
    private final UserRedditRepository userRedditRepository;

    public PostResponseDto create(PostDto postDto) throws IOException {
        String imageUrl=imageUploadToS3(postDto.getImage());
        System.out.println("File Upload Complete.The url is "+imageUrl);
        Post post=mapDtoToPost(postDto);
        post.setUserReddit(utilityService.getLoggedInUser());
        post.setImageUrl(imageUrl);
        postRepository.save(post);
        return mapPostToDto(post);

    }
    public String imageUploadToS3(MultipartFile multipartFile) throws IOException {

        String fileName=multipartFile.getName();
        ObjectMetadata objectMetadata=new ObjectMetadata();
        Map<String,String> myMetadata=new HashMap<>();
        myMetadata.put("fileName",fileName);//stores the filename for every file as user metadata in Object Metadata
        objectMetadata.setUserMetadata(myMetadata);

        String uuid=UUID.randomUUID().toString();//generates random name for every object stored in bucket(Key)
        PutObjectRequest putObjectRequest=new PutObjectRequest("springboot-post",uuid,multipartFile.getInputStream(),objectMetadata);
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);//Public Read access to S3 Object
        PutObjectResult putObjectResult=amazonS3Client.putObject(putObjectRequest);
        return amazonS3Client.getResourceUrl("springboot-post",uuid);

    }
    public Post mapDtoToPost(PostDto postDto)
    {
        Post post=new Post();
        post.setCreatedAt(Instant.now());
        post.setDescription(postDto.getDescription());
        post.setPostTitle(postDto.getPostTitle());
       Optional<SubReddit> subReddit=subRedditRepository.findByTopicName(postDto.getSubRedditName());
       subReddit.orElseThrow(()->
               new SpringRedditException("You cannot post.Subreddit with this name "+postDto.getSubRedditName()+" does not exist"));
       post.setSubReddit(subReddit.get());
       post.setVoteCount(0);
       return post;
    }
    public PostResponseDto mapPostToDto(Post post)
    {
        PostResponseDto postResponseDto=new PostResponseDto();
        postResponseDto.setId(post.getId());
        postResponseDto.setPostTitle(post.getPostTitle());
        postResponseDto.setDescription(post.getDescription());
        postResponseDto.setSubRedditName(post.getSubReddit().getTopicName());
        postResponseDto.setImageUrl(post.getImageUrl());
        postResponseDto.setVoteCount(post.getVoteCount());
        postResponseDto.setCreatedAt(post.getCreatedAt());
        return postResponseDto;
    }

    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream().map(this::mapPostToDto).collect(Collectors.toList());
    }

    public PostResponseDto getPostById(Long id) {
        Optional<Post> post=postRepository.findById(id);
        post.orElseThrow(()->new SpringRedditException("Post with the id "+id+" does not exist"));
        return mapPostToDto(post.get());
    }

    public List<PostResponseDto> getAllPostsInSubReddit(String subRedditName) {
        Optional<SubReddit> subReddit=subRedditRepository.findByTopicName(subRedditName);
        subReddit.orElseThrow(()->new SpringRedditException(
                "No SubReddit with the "+subRedditName +" name exists"));
        List<Post> posts=postRepository.findAllBySubReddit(subReddit.get());
        return posts.stream().map(this::mapPostToDto).collect(Collectors.toList());
    }



}
