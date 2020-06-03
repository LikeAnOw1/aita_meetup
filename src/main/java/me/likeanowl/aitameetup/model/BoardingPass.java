package me.likeanowl.aitameetup.model;

import lombok.Value;

import java.time.Instant;
import java.time.LocalDateTime;

@Value
public class BoardingPass {
    long id;
    long guestId;
    String destination;
    LocalDateTime arrivalDate;
    String invitationCode;
    boolean checkedIn;
    Instant checkedInAt;

    @SuppressWarnings("unused")
    public BoardingPass(Long id,
                        Long guestId,
                        String destination,
                        LocalDateTime arrivalDate,
                        String invitationCode,
                        Boolean checkedIn,
                        Instant checkedInAt) {
        this.id = id;
        this.guestId = guestId;
        this.destination = destination;
        this.arrivalDate = arrivalDate;
        this.invitationCode = invitationCode;
        this.checkedIn = checkedIn;
        this.checkedInAt = checkedInAt;
    }

    public BoardingPass(long guestId,
                        String destination,
                        LocalDateTime arrivalDate,
                        String invitationCode) {
        this.id = 0;
        this.guestId = guestId;
        this.destination = destination;
        this.arrivalDate = arrivalDate;
        this.invitationCode = invitationCode;
        this.checkedIn = false;
        this.checkedInAt = null;
    }
}
