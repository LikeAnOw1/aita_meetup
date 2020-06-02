package me.likeanowl.aitameetup.model;

import lombok.Value;

import java.time.Instant;
import java.time.LocalDateTime;

@Value
public class BoardingPass {
    long id;
    long passengerId;
    String destination;
    LocalDateTime arrivalDate;
    String invitationCode;
    boolean checkedIn;
    Instant checkedInAt;
}
