package me.likeanowl.aitameetup.controller;

import lombok.RequiredArgsConstructor;
import me.likeanowl.aitameetup.controller.requests.CheckInRequest;
import me.likeanowl.aitameetup.controller.requests.GenerateBoardingPassRequest;
import me.likeanowl.aitameetup.model.BoardingPass;
import me.likeanowl.aitameetup.service.BoardingPassService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rest/v1")
public class BoardingPassController {

    private final BoardingPassService boardingPassService;

    @PostMapping(value = "boarding",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BoardingPass> generateBoardingPass(@RequestBody GenerateBoardingPassRequest request) {
        return ResponseEntity.ok(boardingPassService.generateBoardingPass(
                request.getGuestId(), request.getDestination(), request.getArrival())
        );
    }

    @PostMapping(value = "boarding/checkin")
    public ResponseEntity<BoardingPass> checkIn(@RequestBody CheckInRequest request) {
        return ResponseEntity.ok(boardingPassService.checkIn(request.getInvitationCode()));
    }
}

