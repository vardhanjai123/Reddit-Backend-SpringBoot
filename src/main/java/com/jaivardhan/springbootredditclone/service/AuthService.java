package com.jaivardhan.springbootredditclone.service;
import com.jaivardhan.springbootredditclone.dto.AuthenticationResponse;
import com.jaivardhan.springbootredditclone.dto.LoginRequest;
import com.jaivardhan.springbootredditclone.dto.RefreshTokenRequestDto;
import com.jaivardhan.springbootredditclone.dto.RegisterRequest;
import com.jaivardhan.springbootredditclone.exceptions.SpringRedditException;
import com.jaivardhan.springbootredditclone.model.NotificationEmail;
import com.jaivardhan.springbootredditclone.model.RefreshToken;
import com.jaivardhan.springbootredditclone.model.UserReddit;
import com.jaivardhan.springbootredditclone.model.VerificationToken;
import com.jaivardhan.springbootredditclone.repository.UserRedditRepository;
import com.jaivardhan.springbootredditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {


    private final  UserRedditRepository userRedditRepository;

    private final PasswordEncoder passwordEncoder;

    private final  VerificationTokenRepository verificationTokenRepository;

    private final   MailSendService mailSendService;

    private final   AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final  UserDetailsService userDetailsService;

    private final RefreshTokenService refreshTokenService;

    private Clock clock;


    @Transactional
    public void registerUser(RegisterRequest registerRequest)
    {
        UserReddit userReddit=new UserReddit();

        userReddit.setUserName(registerRequest.getUserName());
        userReddit.setEmail(registerRequest.getEmail());
        userReddit.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userReddit.setEnabled(false);
        Instant instant=clock.instant();
        userReddit.setCreatedAt(instant);
        userRedditRepository.save(userReddit);
        String token=generateVerificationToken(userReddit);
        mailSendService.sendMail(new NotificationEmail("Account Activation",userReddit.getEmail(),
                "Thanks for signing up.Please verify your email for further proceedings by clicking on the url "+
                        "http://localhost:8080/api/auth/verificationToken/"+token));

    }

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

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication;
        try {
           authentication = authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                   loginRequest.getUserName(),
                    loginRequest.getPassword()));
        }catch (DisabledException e)
        {

            throw new SpringRedditException("User is disabled!!Please enable the user first to LOGIN");
        }
        catch (AuthenticationException e)
        {
            throw new SpringRedditException("Authentication Failed!!!");
        }
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user= userDetailsService.loadUserByUsername(loginRequest.getUserName());
        String jwtToken=jwtProvider.generateToken(user);
        RefreshToken refreshToken=refreshTokenService.generateRefreshToken();
        Instant instant=clock.instant();

        return new AuthenticationResponse(loginRequest.getUserName(),jwtToken,instant.plusMillis(900*1000),refreshToken.getToken());

    }

    public AuthenticationResponse refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
            refreshTokenService.validateRefreshToken(refreshTokenRequestDto.getRefreshToken());
             UserDetails user=userDetailsService.loadUserByUsername(refreshTokenRequestDto.getUserName());
             String jwtToken=jwtProvider.generateToken(user);
              Instant instant=clock.instant();
             return new AuthenticationResponse(refreshTokenRequestDto.getUserName(),
                     jwtToken,instant.plusMillis(900*1000),refreshTokenRequestDto.getRefreshToken());
    }
}
