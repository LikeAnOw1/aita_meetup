package me.likeanowl.aitameetup.errors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardingPassErrors {
    public static class BoardingPassAlreadyExists extends RuntimeException {
        public BoardingPassAlreadyExists(long guestId) {
            super(String.format("Boarding pass for guest %d already exists", guestId));
        }

    }
    public static class BoardingPassDoesNotExist extends RuntimeException {
        public BoardingPassDoesNotExist(String invitationCode) {
            super(String.format("Boarding pass for invitationCode: %s does not exist", invitationCode));
        }

    }
}
