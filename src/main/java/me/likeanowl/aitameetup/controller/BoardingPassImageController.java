package me.likeanowl.aitameetup.controller;

import lombok.RequiredArgsConstructor;
import me.likeanowl.aitameetup.controller.requests.GenerateBoardingPassRequest;
import me.likeanowl.aitameetup.service.BoardingPassImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rest/v1")
public class BoardingPassImageController {

    private final BoardingPassImageService boardingPassService;

    @PostMapping(value = "boarding", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateBoardingPass(@RequestBody GenerateBoardingPassRequest request) {
        return ResponseEntity.ok(
                boardingPassService.generateBoardingPassImage(
                        request.getGuestId(), request.getDestination(), request.getArrival())
        );

    }
}

