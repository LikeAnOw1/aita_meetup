package me.likeanowl.aitameetup.repository;

import me.likeanowl.aitameetup.model.BoardingPass;
import org.apache.ibatis.annotations.Param;

public interface BoardingPassRepository {
    void insertBoardingPass(@Param("boarding_pass") BoardingPass boardingPass);
}
