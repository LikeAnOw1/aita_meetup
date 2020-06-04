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
        assertNotNull(boardingPass);

        assertThrows(BoardingPassErrors.BoardingPassAlreadyExists.class,
                () -> boardingPassService.generateBoardingPass(guestId, destination, arrivalDate));
        assertThrows(GuestErrors.GuestDoesNotExist.class,
                () -> boardingPassService.generateBoardingPass(-1, destination, arrivalDate));
        assertFalse(boardingPass.isCheckedIn());

        var guest = boardingPassService.checkIn(boardingPass.getInvitationCode());
        assertNotNull(guest);

        var savedBoardingPass = boardingPassMapper.getBoardingPass(boardingPass.getId());
        assertNotNull(savedBoardingPass);
        assertTrue(savedBoardingPass.isCheckedIn());

        assertThrows(BoardingPassErrors.BoardingPassDoesNotExist.class,
                () -> boardingPassService.checkIn("this code is not valid"));
        assertThrows(GuestErrors.GuestAlreadyCheckedIn.class,
                () -> boardingPassService.checkIn(boardingPass.getInvitationCode()));
    }
}