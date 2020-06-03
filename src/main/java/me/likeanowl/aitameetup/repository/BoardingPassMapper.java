package me.likeanowl.aitameetup.repository;

import me.likeanowl.aitameetup.model.BoardingPass;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface BoardingPassMapper {
    BoardingPass insertBoardingPass(@Param("boardingPass") BoardingPass boardingPass);

    boolean boardingPassExists(@Param("guest_id") long guestId,
                               @Param("destination") String destination,
                               @Param("arrival_date") LocalDateTime arrivalDate);

    BoardingPass findBoardingPass(@Param("invitation_code") String invitationCode);

    BoardingPass checkIn(@Param("id") long boardingPassId);
}
