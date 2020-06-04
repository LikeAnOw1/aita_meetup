package me.likeanowl.aitameetup.controller;

import lombok.RequiredArgsConstructor;
import me.likeanowl.aitameetup.model.Guest;
import me.likeanowl.aitameetup.service.GuestListenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rest/v1")
public class GuestController {

    private final GuestListenerService guestListenerService;

    @GetMapping(value = "/guests", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Guest> getRandomGuestStream() {
        return Flux.create(guestListenerService::registerListener);
    }
}
