package com.jaivardhan.springbootredditclone.commonbeans;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Clock;

@Configuration
public class CommonBeans {
    @Bean
    @Primary
    public AmazonS3Client getAmazonS3Client()
    {
        AmazonS3ClientBuilder amazonS3ClientBuilder=AmazonS3Client.builder();
        amazonS3ClientBuilder.setRegion("ap-south-1");
        AmazonS3Client amazonS3Client=(AmazonS3Client)amazonS3ClientBuilder.build();
        return amazonS3Client;
    }

    @Bean
    public Clock getClock()
    {
        Clock clock=Clock.systemUTC();
        return clock;
    }

}
