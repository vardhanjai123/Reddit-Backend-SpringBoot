package com.jaivardhan.springbootredditclone.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SpringRedditException extends RuntimeException {

    public SpringRedditException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
