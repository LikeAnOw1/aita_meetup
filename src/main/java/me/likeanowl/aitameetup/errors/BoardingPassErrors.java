package me.likeanowl.aitameetup.errors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardingPassErrors {
    public static class BoardingPassAlreadyExists extends NetworkException {
        public BoardingPassAlreadyExists(long guestId) {
            super(String.format("Boarding pass for guest '%d' already exists", guestId),
                    HttpStatus.BAD_REQUEST);
        }
    }

    public static class BoardingPassDoesNotExist extends NetworkException {
        public BoardingPassDoesNotExist(String invitationCode) {
            super(String.format("Boarding pass for invitationCode '%s' does not exist", invitationCode),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
