package me.likeanowl.aitameetup.controller.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import me.likeanowl.aitameetup.model.Guest;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AddGuestsRequest {
    @Valid
    @NotEmpty(message = "guests should not be empty")
    private List<GuestDTO> guests;

    @Data
    public static class GuestDTO {
        @Min(value = 1, message = "id should be present")
        private long id;

        @JsonProperty("first_name")
        @Size(max = 50, message = "first_name max size is 50 chars")
        @NotBlank(message = "first_name should be present")
        private String firstName;

        @JsonProperty("last_name")
        @Size(max = 50, message = "last_name max size is 50 chars")
        @NotBlank(message = "last_name should be present")
        private String lastName;
        @JsonProperty("flight_distance")
        @Min(value = 1, message = "flight_distance should be present")
        private int flightDistance;
        @JsonProperty("flight_hours")
        @Min(value = 1, message = "flight_hours should be present")
        private int flightHours;

        public static Guest toGuest(GuestDTO dto) {
            return new Guest(dto.id, dto.firstName, dto.lastName, dto.flightDistance, dto.flightHours);
        }
    }
}
