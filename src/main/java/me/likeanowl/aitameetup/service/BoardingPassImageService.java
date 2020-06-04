package me.likeanowl.aitameetup.service;

import lombok.RequiredArgsConstructor;
import me.likeanowl.aitameetup.images.BarcodeImageGenerator;
import me.likeanowl.aitameetup.images.BoardingPassImageGenerator;
import me.likeanowl.aitameetup.model.BoardingPass;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class BoardingPassImageService {
    private final BoardingPassService boardingPassService;
    private final BarcodeImageGenerator barcodeImageGenerator;
    private final BoardingPassImageGenerator boardingPassImageGenerator;
    private final ExecutorService worker = Executors.newFixedThreadPool(10);

    public byte[] generateBoardingPassImage(long passengerId,
                                            String destination,
                                            LocalDateTime arrivalDate) {
        var boardingPass = boardingPassService.generateBoardingPass(passengerId, destination, arrivalDate);
        var barcodeImage = barcodeImageGenerator.buildBarcodeImage(boardingPass.getInvitationCode());
        return boardingPassImageGenerator.buildBoardingPassImage();
    }

    private CompletableFuture<BoardingPass> generateBoardingPass(long passengerId,
                                                                 String destination,
                                                                 LocalDateTime arrivalDate) {
        return CompletableFuture.supplyAsync(() ->
                boardingPassService.generateBoardingPass(passengerId, destination, arrivalDate), worker);
    }
}
