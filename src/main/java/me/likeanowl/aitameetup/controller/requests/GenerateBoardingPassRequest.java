package me.likeanowl.aitameetup.controller.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class GenerateBoardingPassRequest {

    @Min(value = 1, message = "guest_id should be present")
    @JsonProperty("guest_id")
    private long guestId;

    @NotBlank(message = "destination should be present")
    @Size(max = 300, message = "destination max size is 300 chars")
    @JsonProperty("destination")
    private String destination;

    @NotNull(message = "arrival date should be present")
    @JsonProperty("arrival")
    private LocalDateTime arrival;
}
