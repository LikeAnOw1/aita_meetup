package me.likeanowl.aitameetup.controller;


import lombok.RequiredArgsConstructor;
import me.likeanowl.aitameetup.controller.requests.CheckInRequest;
import me.likeanowl.aitameetup.model.Guest;
import me.likeanowl.aitameetup.service.CheckinListenerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rest/v1")
public class CheckInController {
    private final CheckinListenerService checkinService;

    @PostMapping(value = "checkin")
    public ResponseEntity<Guest> checkIn(@RequestBody CheckInRequest request) {
        return ResponseEntity.ok(checkinService.checkIn(request.getInvitationCode()));
    }

    @GetMapping(value = "checkin", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<Guest>> getLatestCheckInStream() {
        return ResponseEntity.ok(Flux.create(checkinService::registerListener));
    }
}
