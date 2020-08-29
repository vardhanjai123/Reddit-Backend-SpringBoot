package com.jaivardhan.springbootredditclone;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Test
    public void registerTest()
    {
        System.out.println(Thread.currentThread().getId());
        String url = "/api/auth/register";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonbody="{\n" +
                "    \"userName\":\"jaivardhan2\",\n" +
                "    \"email\":\"shekharjai07@gmail.com\",\n" +
                "    \"password\":\"asdf1234\"\n" +
                "}";
        HttpEntity entity = new HttpEntity<>(jsonbody,headers);
        ResponseEntity<Object> responseEntity =
                testRestTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Object>() {
                });

    }
}
