package me.likeanowl.aitameetup.controller;


import lombok.RequiredArgsConstructor;
import me.likeanowl.aitameetup.controller.requests.CheckInRequest;
import me.likeanowl.aitameetup.model.BoardingPass;
import me.likeanowl.aitameetup.service.BoardingPassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rest/v1")
public class CheckInController {
    private final BoardingPassService boardingPassService;

    @PostMapping(value = "checkin")
    public ResponseEntity<BoardingPass> checkIn(@RequestBody CheckInRequest request) {
        return ResponseEntity.ok(boardingPassService.checkIn(request.getInvitationCode()));
    }
}
