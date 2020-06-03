package me.likeanowl.aitameetup.model;

import lombok.Value;

@Value
public class Guest {
    long id;
    String firstName;
    String lastName;
    int flightDistance;
    int flightHours;
}
