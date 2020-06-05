package me.likeanowl.aitameetup.controller;

import lombok.RequiredArgsConstructor;
import me.likeanowl.aitameetup.controller.requests.GenerateBoardingPassRequest;
import me.likeanowl.aitameetup.service.BoardingPassImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rest/v1")
public class BoardingPassImageController {

    private final BoardingPassImageService boardingPassService;

    @PostMapping(value = "boarding", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<byte[]> generateBoardingPass(@Valid @RequestBody GenerateBoardingPassRequest request) {
        return boardingPassService.generateBoardingPassImage(
                        request.getGuestId(), request.getDestination(), request.getArrival());

    }
}

