package me.likeanowl.aitameetup.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NetworkException extends RuntimeException {
    private final HttpStatus status;

    public NetworkException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
