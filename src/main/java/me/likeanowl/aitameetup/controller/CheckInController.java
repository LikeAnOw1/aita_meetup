package me.likeanowl.aitameetup.controller;


import lombok.RequiredArgsConstructor;
import me.likeanowl.aitameetup.controller.requests.CheckInRequest;
import me.likeanowl.aitameetup.model.Guest;
import me.likeanowl.aitameetup.service.CheckinListenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rest/v1")
public class CheckInController {
    private final CheckinListenerService checkinService;

    @PostMapping(value = "checkin")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Guest checkIn(@Valid @RequestBody CheckInRequest request) {
        return checkinService.checkIn(request.getInvitationCode());
    }

    @GetMapping(value = "checkin", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Guest> getLatestCheckInStream() {
        return Flux.create(checkinService::registerListener);
    }
}
