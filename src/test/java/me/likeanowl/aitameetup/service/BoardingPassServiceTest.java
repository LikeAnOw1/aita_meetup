package me.likeanowl.aitameetup.service;

import me.likeanowl.aitameetup.errors.BoardingPassErrors;
import me.likeanowl.aitameetup.errors.GuestErrors;
import me.likeanowl.aitameetup.model.BoardingPass;
import me.likeanowl.aitameetup.model.Guest;
import me.likeanowl.aitameetup.repository.BoardingPassMapper;
import me.likeanowl.aitameetup.repository.GuestMapper;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = {BoardingPassService.class})
class BoardingPassServiceTest {

    @MockBean
    private BoardingPassMapper boardingPassMapper;
    @MockBean
    private GuestMapper guestMapper;

    @Autowired
    private BoardingPassService boardingPassService;

    private final long guestId = 1L;
    private final String destination = "Moscow";
    private final LocalDateTime arrivalDate = LocalDateTime.of(2020, 6, 10, 19, 0);
    private final String invitationCode = "TEST/TEST       TESTCODE";
    private final BoardingPass notCheckedIn = new BoardingPass(1, guestId, destination,
            arrivalDate, invitationCode, false, null);
    private final BoardingPass checkedIn = new BoardingPass(1, guestId, destination,
            arrivalDate, invitationCode, true, Instant.now());
    private final Guest guest = new Guest(guestId, "firstname", "lastname",
            1000, 10);


    @Test
    void generateBoardingPass_duplicate() {
        doReturn(true).when(boardingPassMapper).boardingPassExists(anyLong(), anyString(), any(LocalDateTime.class));
        assertThrows(BoardingPassErrors.BoardingPassAlreadyExists.class,
                () -> boardingPassService.generateBoardingPass(guestId, destination, arrivalDate));
    }

    @Test
    void generateBoardingPass_noGuest() {
        doReturn(false).when(boardingPassMapper).boardingPassExists(anyLong(), anyString(), any(LocalDateTime.class));
        doReturn(null).when(guestMapper).findGuest(anyLong());
        assertThrows(GuestErrors.GuestDoesNotExist.class,
                () -> boardingPassService.generateBoardingPass(guestId, destination, arrivalDate));
    }

    @Test
    void generateBoardingPass_valid() {
        doReturn(false)
                .when(boardingPassMapper).boardingPassExists(anyLong(), anyString(), any(LocalDateTime.class));
        doReturn(guest).when(guestMapper).findGuest(eq(guestId));
        doAnswer(AdditionalAnswers.returnsFirstArg())
                .when(boardingPassMapper).insertBoardingPass(any(BoardingPass.class));

        var boardingPass = boardingPassService.generateBoardingPass(guestId, destination, arrivalDate);
        assertAll(
                () -> assertNotNull(boardingPass),
                () -> assertEquals(guestId, boardingPass.getGuestId()),
                () -> assertEquals(destination, boardingPass.getDestination()),
                () -> assertEquals(arrivalDate, boardingPass.getArrivalDate()),
                () -> assertTrue(boardingPass.getInvitationCode().startsWith("LASTNAME/FIRSTNAME       ")),
                () -> assertFalse(boardingPass.isCheckedIn()),
                () -> assertNull(boardingPass.getCheckedInAt())
        );
    }

    @Test
    void checkIn_noBoardingPass() {
        doReturn(null).when(boardingPassMapper).findBoardingPass(anyString());
        assertThrows(BoardingPassErrors.BoardingPassDoesNotExist.class,
                () -> boardingPassService.checkIn(invitationCode));
    }

    @Test
    void checkIn_checkedIn() {
        doReturn(checkedIn).when(boardingPassMapper).findBoardingPass(anyString());
        assertThrows(GuestErrors.GuestAlreadyCheckedIn.class,
                () -> boardingPassService.checkIn(invitationCode));
    }

    @Test
    void checkIn_notCheckedIn() {
        doReturn(notCheckedIn).when(boardingPassMapper).findBoardingPass(invitationCode);
        doReturn(guest).when(boardingPassMapper).checkIn(notCheckedIn.getId());
        var checkIn = boardingPassService.checkIn(invitationCode);
        assertEquals(guest, checkIn);
    }
}