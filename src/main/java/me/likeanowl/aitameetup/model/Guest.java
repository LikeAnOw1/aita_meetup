package me.likeanowl.aitameetup.model;

import lombok.Value;

@Value
public class Guest {
    long id;
    String firstName;
    String lastName;
    int flightDistance;
    int flightHours;

    public Guest(long id, String firstName, String lastName, int flightDistance, int flightHours) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.flightDistance = flightDistance;
        this.flightHours = flightHours;
    }
}
