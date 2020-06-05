package me.likeanowl.aitameetup.controller;

import lombok.RequiredArgsConstructor;
import me.likeanowl.aitameetup.controller.requests.AddGuestsRequest;
import me.likeanowl.aitameetup.model.Guest;
import me.likeanowl.aitameetup.service.GuestListenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rest/v1")
public class GuestController {

    private final GuestListenerService guestService;

    @GetMapping(value = "/guest", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Guest> getRandomGuestStream() {
        return Flux.create(guestService::registerListener);
    }

    @PostMapping(value = "/guest")
    @ResponseStatus(HttpStatus.CREATED)
    public String addGuests(@Valid @RequestBody AddGuestsRequest request) {
        guestService.insertGuests(request.getGuests().stream()
                .map(AddGuestsRequest.GuestDTO::toGuest).collect(Collectors.toList()));
        return String.format("Added %d guests", request.getGuests().size());
    }
}
