package me.likeanowl.aitameetup.controller.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CheckInRequest {
    @JsonProperty("invitation_code")
    private String invitationCode;
}
