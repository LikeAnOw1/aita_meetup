package me.likeanowl.aitameetup.service;

import me.likeanowl.aitameetup.model.Guest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CheckinListenerService.class})
class CheckinListenerServiceTest {

    @Autowired
    private CheckinListenerService checkinListenerService;

    @MockBean
    private BoardingPassService boardingPassService;

    private final Guest previous = new Guest(0, "previous", "previous", 100, 1);
    private final Guest first = new Guest(1, "first", "first", 500, 2);
    private final Guest second = new Guest(2, "second", "second", 1000, 3);


    @BeforeEach
    void setup() {
        doReturn(previous).when(boardingPassService).getLastCheckedInGuest();
        checkinListenerService.setup();
    }

    @Test
    void checkInListeners() {
        doReturn(first, second).when(boardingPassService).checkIn(anyString());
        var firstListener = Flux.create(checkinListenerService::registerListener);
        var secondListener = Flux.create(checkinListenerService::registerListener);

        var firstVerifier = StepVerifier.create(firstListener)
                .assertNext(g -> assertEquals(previous, g))
                .assertNext(g -> assertEquals(first, g))
                .assertNext(g -> assertEquals(second, g))
                .thenCancel()
                .verifyLater();

        var secondVerifier = StepVerifier.create(secondListener)
                .assertNext(g -> assertEquals(previous, g))
                .assertNext(g -> assertEquals(first, g))
                .assertNext(g -> assertEquals(second, g))
                .thenCancel()
                .verifyLater();

        var guest = checkinListenerService.checkIn("invitation");
        assertEquals(first, guest);

        var thirdListener = Flux.create(checkinListenerService::registerListener);

        var thirdVerifier = StepVerifier.create(thirdListener)
                .assertNext(g -> assertEquals(first, g))
                .assertNext(g -> assertEquals(second, g))
                .thenCancel()
                .verifyLater();

        guest = checkinListenerService.checkIn("invitation2");
        assertEquals(second, guest);

        firstVerifier.verify();
        secondVerifier.verify();
        thirdVerifier.verify();
    }
}