package me.likeanowl.aitameetup.service;

import me.likeanowl.aitameetup.BaseIntegrationTest;
import me.likeanowl.aitameetup.errors.BoardingPassErrors;
import me.likeanowl.aitameetup.errors.GuestErrors;
import me.likeanowl.aitameetup.repository.BoardingPassMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardingPassServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private BoardingPassService boardingPassService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private BoardingPassMapper boardingPassMapper;

    private final long guestId = 1L;
    private final String destination = "Moscow";
    private final LocalDateTime arrivalDate = LocalDateTime.parse("2020-06-10T18:00:00");

    @Test
    void testBoardingPassGenerationAndCheckIn() {
        var boardingPass = boardingPassService.generateBoardingPass(guestId, destination, arrivalDate);
        assertNotNull(boardingPass, "Boarding pass after generation shouldn't be null");

        assertThrows(BoardingPassErrors.BoardingPassAlreadyExists.class,
                () -> boardingPassService.generateBoardingPass(guestId, destination, arrivalDate),
                "Shouldn't generate boarding pass for same guest, destination and arrivalDate");
        assertThrows(GuestErrors.GuestDoesNotExist.class,
                () -> boardingPassService.generateBoardingPass(-1, destination, arrivalDate),
                "Shouldn't generate boarding pass for guest that not exists");
        assertFalse(boardingPass.isCheckedIn());

        var guest = boardingPassService.checkIn(boardingPass.getInvitationCode());
        assertNotNull(guest, "Guest after checkin should not be null");

        var savedBoardingPass = boardingPassMapper.getBoardingPass(boardingPass.getId());
        assertNotNull(savedBoardingPass, "Boarding pass should be present in db");
        assertTrue(savedBoardingPass.isCheckedIn(), "Boarding pass wasn't checked in after checkin");

        assertThrows(BoardingPassErrors.BoardingPassDoesNotExist.class,
                () -> boardingPassService.checkIn("this code is not valid"),
                "Should not be able to check in with non existent invitation code");
        assertThrows(GuestErrors.GuestAlreadyCheckedIn.class,
                () -> boardingPassService.checkIn(boardingPass.getInvitationCode()),
                "Guest should not be able to checkin more than once");
    }
}