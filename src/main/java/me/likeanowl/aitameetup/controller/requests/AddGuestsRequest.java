package me.likeanowl.aitameetup.controller.requests;

import lombok.Data;
import me.likeanowl.aitameetup.controller.responses.GuestDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AddGuestsRequest {
    @Valid
    @NotEmpty(message = "guests should not be empty")
    private List<GuestDTO> guests;
}
