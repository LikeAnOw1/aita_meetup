package me.likeanowl.aitameetup.service;

import com.google.common.util.concurrent.Striped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.likeanowl.aitameetup.model.BoardingPass;
import me.likeanowl.aitameetup.model.Guest;
import me.likeanowl.aitameetup.repository.BoardingPassMapper;
import me.likeanowl.aitameetup.repository.GuestMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;

import static me.likeanowl.aitameetup.errors.BoardingPassErrors.BoardingPassAlreadyExists;
import static me.likeanowl.aitameetup.errors.BoardingPassErrors.BoardingPassDoesNotExist;
import static me.likeanowl.aitameetup.errors.GuestErrors.GuestAlreadyCheckedIn;
import static me.likeanowl.aitameetup.errors.GuestErrors.GuestDoesNotExist;


@Slf4j
@Service
@RequiredArgsConstructor
public class BoardingPassService {
    private final BoardingPassMapper boardingPassMapper;
    private final GuestMapper guestMapper;
    private final Striped<Lock> checkInLocks = Striped.lazyWeakLock(128);

    public BoardingPass generateBoardingPass(long guestId,
                                             String destination,
                                             LocalDateTime arrivalDate) {
        if (boardingPassMapper.boardingPassExists(guestId, destination, arrivalDate)) {
            log.info("Tried to create duplicate boarding pass for guest: {}, to destination: {}, at: {}",
                    guestId, destination, arrivalDate);
            throw new BoardingPassAlreadyExists(guestId);
        }

        var guest = guestMapper.findGuest(guestId);
        if (guest == null) {
            log.info("Tried to create boarding pass for non existent guest: {}", guestId);
            throw new GuestDoesNotExist(guestId);
        }

        var boardingPass = new BoardingPass(guestId, destination, arrivalDate, generateInvitationCode(guest));
        return boardingPassMapper.insertBoardingPass(boardingPass);
    }

    //TODO need some kind of meetup identifier
    public Guest getLastCheckedInGuest() {
        return boardingPassMapper.getLastCheckedInGuest();
    }

    public Guest checkIn(String invitationCode) {
        var lock = checkInLocks.get(invitationCode);
        try {
            lock.lock();
            return checkInUnsafe(invitationCode);
        } finally {
            lock.unlock();
        }
    }

    private Guest checkInUnsafe(String invitationCode) {
        var boardingPass = boardingPassMapper.findBoardingPass(invitationCode);
        validateOnCheckIn(invitationCode, boardingPass);
        return boardingPassMapper.checkIn(boardingPass.getId());
    }

    private void validateOnCheckIn(String invitationCode, BoardingPass boardingPass) {
        if (boardingPass == null) {
            log.info("Boarding pass with invitationCode: {} does not exist", invitationCode);
            throw new BoardingPassDoesNotExist(invitationCode);
        }
        if (boardingPass.isCheckedIn()) {
            log.info("Guest with id {} is already checked in", boardingPass.getGuestId());
            throw new GuestAlreadyCheckedIn(boardingPass.getGuestId(), invitationCode);
        }
    }

    private String generateInvitationCode(Guest guest) {
        var firstName = guest.getFirstName();
        var lastName = guest.getLastName();
        return String.format("%s/%s       %s", lastName, firstName, randomString()).toUpperCase();
    }

    private String randomString() {
        return RandomStringUtils.randomAlphanumeric(16);
    }
}
