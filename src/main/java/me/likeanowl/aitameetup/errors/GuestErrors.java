package me.likeanowl.aitameetup.errors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuestErrors {
    public static class GuestDoesNotExist extends NetworkException {
        public GuestDoesNotExist(long guestId) {
            super(String.format("Unable to create boarding pass, guest '%d' does not exist", guestId),
                    HttpStatus.BAD_REQUEST);
        }
    }
    public static class GuestAlreadyCheckedIn extends NetworkException {
        public GuestAlreadyCheckedIn(long guestId, String invitationCode) {
            super(String.format("Guest '%d' already checked in with code '%s'", guestId, invitationCode),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
