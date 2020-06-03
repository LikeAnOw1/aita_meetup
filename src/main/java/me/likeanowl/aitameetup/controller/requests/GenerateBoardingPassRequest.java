package me.likeanowl.aitameetup.controller.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GenerateBoardingPassRequest {
    @JsonProperty("guest_id")
    private long guestId;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("arrival")
    private LocalDateTime arrival;
}
