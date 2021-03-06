package me.likeanowl.aitameetup.repository;

import me.likeanowl.aitameetup.model.BoardingPass;
import me.likeanowl.aitameetup.model.Guest;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface BoardingPassMapper {
    BoardingPass insertBoardingPass(@Param("boardingPass") BoardingPass boardingPass);

    BoardingPass getBoardingPass(@Param("id") long id);

    boolean boardingPassExists(@Param("guest_id") long guestId,
                               @Param("destination") String destination,
                               @Param("arrival_date") LocalDateTime arrivalDate);

    BoardingPass findBoardingPass(@Param("invitation_code") String invitationCode);

    Guest checkIn(@Param("id") long boardingPassId);

    Guest getLastCheckedInGuest();
}
