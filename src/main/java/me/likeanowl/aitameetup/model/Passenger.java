package me.likeanowl.aitameetup.model;

import lombok.Value;

@Value
public class Passenger {
    long id;
    String firstName;
    String lastName;
    long flightDistance;
    long flightHours;
}
