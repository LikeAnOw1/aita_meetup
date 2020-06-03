package me.likeanowl.aitameetup.errors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuestErrors {
    public static class GuestDoesNotExist extends RuntimeException {
        public GuestDoesNotExist(long guestId) {
            super(String.format("Unable to create boarding pass, guest %d does not exist", guestId));
        }

    }
    public static class GuestAlreadyCheckedIn extends RuntimeException {
        public GuestAlreadyCheckedIn(long guestId, String invitationCode) {
            super(String.format("Guest %d already checked in with code %s", guestId, invitationCode));
        }
    }
}
