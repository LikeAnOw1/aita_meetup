package me.likeanowl.aitameetup.controller.responses;

import me.likeanowl.aitameetup.model.Guest;

public class DTOMapper {

    public static Guest toGuest(GuestDTO dto) {
        return new Guest(dto.getId(), dto.getFirstName(),
                dto.getLastName(), dto.getFlightDistance(), dto.getFlightHours());
    }

    public static GuestDTO fromGuest(Guest guest) {
        return new GuestDTO(guest.getId(), guest.getFirstName(),
                guest.getLastName(), guest.getFlightDistance(), guest.getFlightHours());
    }
}
