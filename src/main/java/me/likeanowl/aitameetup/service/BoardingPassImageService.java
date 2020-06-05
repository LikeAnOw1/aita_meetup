package me.likeanowl.aitameetup.service;

import lombok.RequiredArgsConstructor;
import me.likeanowl.aitameetup.images.BarcodeImageGenerator;
import me.likeanowl.aitameetup.images.BoardingPassImageGenerator;
import me.likeanowl.aitameetup.model.BoardingPass;
import me.likeanowl.aitameetup.utils.ExecutorUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
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
    private final ExecutorService worker = Executors.newFixedThreadPool(5);

    public CompletableFuture<byte[]> generateBoardingPassImage(long passengerId,
                                                               String destination,
                                                               LocalDateTime arrivalDate) {
        var boardingPass = generateBoardingPass(passengerId, destination, arrivalDate);
        var barcodeImage = boardingPass.thenApplyAsync(pass ->
                barcodeImageGenerator.buildBarcodeImage(pass.getInvitationCode()), worker);

        return boardingPassImageGenerator.buildBoardingPassImage(boardingPass, barcodeImage);
    }

    private CompletableFuture<BoardingPass> generateBoardingPass(long passengerId,
                                                                 String destination,
                                                                 LocalDateTime arrivalDate) {
        return CompletableFuture.supplyAsync(() ->
                boardingPassService.generateBoardingPass(passengerId, destination, arrivalDate), worker);
    }

    @PreDestroy
    private void tearDown() {
        ExecutorUtils.shutdown(worker);
    }
}
