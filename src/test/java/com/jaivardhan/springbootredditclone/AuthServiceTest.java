package com.jaivardhan.springbootredditclone;
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
import com.jaivardhan.springbootredditclone.service.AuthService;
import com.jaivardhan.springbootredditclone.service.JwtProvider;
import com.jaivardhan.springbootredditclone.service.MailSendService;
import com.jaivardhan.springbootredditclone.service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;

import static java.time.Instant.parse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    UserReddit userReddit;
    LoginRequest loginRequest;
    RefreshTokenRequestDto refreshTokenRequestDto;
    AuthenticationResponse authenticationResponse;
    RegisterRequest registerRequest;
    String instantExpected = "2014-12-22T10:15:30Z";
    Clock clock1 = Clock.fixed(parse(instantExpected), ZoneId.of("UTC"));
    Instant instant = Instant.now(clock1);
    private Class<NotificationEmail> notificationEmail;

    @BeforeEach
    public void initializeUserRedditData()
    {
        //filling the registerRequest data
        registerRequest=new RegisterRequest();
        registerRequest.setUserName("harsh");
        registerRequest.setEmail("harshashish002@gmail.com");
        registerRequest.setPassword("sitaram");

        //filling user data
        userReddit=new UserReddit();
        userReddit.setUserId((long) 1);
        userReddit.setUserName("harsh");
        userReddit.setEmail("harshashish002@gmail.com");
        userReddit.setPassword("sitaram");
        userReddit.setEnabled(false);
        userReddit.setCreatedAt(instant);

        //filling loginRequest data
        loginRequest=new LoginRequest();
        loginRequest.setUserName("harsh");
        loginRequest.setPassword("sitaram");

        //filling refreshTokenRequestDto data
        refreshTokenRequestDto=new RefreshTokenRequestDto();
        refreshTokenRequestDto.setRefreshToken("jwt1");
        refreshTokenRequestDto.setUserName("harsh");

        //filling authenticationResponse data
        authenticationResponse=new AuthenticationResponse();
        authenticationResponse.setJwtToken("jwttest");
        authenticationResponse.setUserName("harsh");
        authenticationResponse.setRefreshToken("jwt1");
        authenticationResponse.setExpiresAt(instant.plusMillis(900000));
    }

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRedditRepository userRedditRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @Mock
    private MailSendService mailSendService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private Clock clock;

    @Test
    public void verifyTokenTest()
    {
        String token="jaivardhan";
        VerificationToken verificationToken=new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setId((long) 1);
        verificationToken.setExpiryDate(Instant.now().plusMillis(900000));
        verificationToken.setUserReddit(userReddit);
if(authService==null)
    System.out.println("true");
        if(verificationTokenRepository==null)
            System.out.println("true");
        when(verificationTokenRepository.findByToken(token)).thenReturn(java.util.Optional.of(verificationToken));
        when(userRedditRepository.findById((long) 1)).thenReturn(java.util.Optional.ofNullable(userReddit));
        authService.verifyToken(token);
        verify(verificationTokenRepository,times(1)).findByToken(token);
        verify(userRedditRepository,times(1)).save(userReddit);
    }

    @Test
    public void registerUserTest()
    {
        userReddit.setUserId(null);
        when(clock.instant()).thenReturn(instant);
        when(passwordEncoder.encode("sitaram")).thenReturn("hello");
        userReddit.setPassword("hello");
        when(userRedditRepository.save(userReddit)).thenReturn(userReddit);
        doNothing().when(mailSendService).sendMail(Mockito.any());
        authService.registerUser(registerRequest);

        verify(mailSendService,times(1)).sendMail(Mockito.any());
        verify(userRedditRepository,times(1)).save(userReddit);
        verify(passwordEncoder,times(1)).encode("sitaram");
    }

    @Test
    public void loginTest_whenCredentialsAreCorrect()
    {
        when(clock.instant()).thenReturn(instant);
        User user=new User("harsh","sitaram",new ArrayList<>());
        when(userDetailsService.loadUserByUsername("harsh")).thenReturn(user);
        when(jwtProvider.generateToken(user)).thenReturn("jwttest");
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setCreatedAt(Instant.now());
        refreshToken.setToken("jwt1");
        refreshToken.setId((long) 1);
        when(refreshTokenService.generateRefreshToken()).thenReturn(refreshToken);
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("harsh","sitaram"))).thenReturn(
               null
       );
      assertEquals(authenticationResponse,authService.login(loginRequest));
    }

    @Test
    public void loginTest_whenCredentialsAreIncorrect()
    {
        loginRequest.setPassword("sitara");
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("harsh","sitara"))).thenThrow(new BadCredentialsException("Wrong credentials entered"));
        SpringRedditException redditException=assertThrows(SpringRedditException.class,()->{authService.login(loginRequest);});
    }

    @Test
    public void loginTest_whenUserIsNotEnabled()
    {
        loginRequest.setUserName("jai");
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("jai","sitaram"))).thenThrow(new DisabledException("s"));
        SpringRedditException redditException=assertThrows(SpringRedditException.class,()->{authService.login(loginRequest);});
        verify(authenticationManager,times(1)).authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));
        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));
    }

    @Test
    public void refreshTokenTest()
    {
        when(clock.instant()).thenReturn(instant);
        doNothing().when(refreshTokenService).validateRefreshToken(refreshTokenRequestDto.getRefreshToken());
        User user=new User("harsh","sitaram",new ArrayList<>());
        when(userDetailsService.loadUserByUsername("harsh")).thenReturn(user);
        when(jwtProvider.generateToken(user)).thenReturn("jwttest");
        assertEquals(authenticationResponse,authService.refreshToken(refreshTokenRequestDto));
    }

    @Test
    public void refreshTokenTest_whenRefreshTokenNotExistInDb()
    {
        doThrow(new SpringRedditException("Refresh token doesnot exists")).when(refreshTokenService)
                .validateRefreshToken(refreshTokenRequestDto.getRefreshToken());
        assertThrows(SpringRedditException.class,()->{authService.refreshToken(refreshTokenRequestDto);});

    }

    @Test
    public void refreshTokenTest_whenWrongUserNameEntered()
    {
        doNothing().when(refreshTokenService).validateRefreshToken(refreshTokenRequestDto.getRefreshToken());
        refreshTokenRequestDto.setUserName("harsh12");//entering wrong username
        when(userDetailsService.loadUserByUsername("harsh12")).thenThrow(new UsernameNotFoundException("Wrong username entered.User doesnot exists"));
        assertThrows(UsernameNotFoundException.class,()->{
            authService.refreshToken(refreshTokenRequestDto);
        });
    }


}
