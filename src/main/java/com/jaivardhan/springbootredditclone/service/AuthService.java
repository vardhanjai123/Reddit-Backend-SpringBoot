package com.jaivardhan.springbootredditclone.service;


import com.jaivardhan.springbootredditclone.dto.RegisterRequest;
import com.jaivardhan.springbootredditclone.exceptions.SpringRedditException;
import com.jaivardhan.springbootredditclone.model.NotificationEmail;
import com.jaivardhan.springbootredditclone.model.UserReddit;
import com.jaivardhan.springbootredditclone.model.VerificationToken;
import com.jaivardhan.springbootredditclone.repository.UserRedditRepository;
import com.jaivardhan.springbootredditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRedditRepository userRedditRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailSendService mailSendService;

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

        String token=generateVerificationToken(userReddit);
        mailSendService.sendMail(new NotificationEmail("Account Activation",userReddit.getEmail(),
                "Thanks for signing up.Please verify your email for further proceedings by clicking on the url "+
                        "http://localhost:8080/api/auth/verificationToken/"+token));

    }
//    @Value("${recipientemail}")
//    private String getRecipientMail(String recipientMail) {
//        System.out.println("***********"+recipientMail+"*********************");
//        return recipientMail;
//    }

    private String generateVerificationToken(UserReddit userReddit) {
        String token=UUID.randomUUID().toString();
        VerificationToken verificationToken=new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUserReddit(userReddit);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyToken(String token) {
        Optional<VerificationToken> verificationToken=verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(()->new SpringRedditException("Token does not exis"));
        fetchUserAndEnable(verificationToken.get());
    }



    private void fetchUserAndEnable(VerificationToken verificationToken) {
        Long userId=verificationToken.getUserReddit().getUserId();
        Optional<UserReddit> userReddit=userRedditRepository.findById(userId);
        userReddit.orElseThrow(()->new SpringRedditException("User does not exist"));
        userReddit.get().setEnabled(true);
        userRedditRepository.save(userReddit.get());

    }
}
