package me.likeanowl.aitameetup.controller.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CheckInRequest {

    @NotBlank(message = "invitation_code should be present")
    @Size(max = 256, message = "max size of invitation_code is 256")
    @JsonProperty("invitation_code")
    private String invitationCode;
}
