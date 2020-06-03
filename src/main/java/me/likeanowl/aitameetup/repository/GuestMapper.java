package me.likeanowl.aitameetup.repository;

import me.likeanowl.aitameetup.model.Guest;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface GuestMapper {
    void insertGuest(@Param("guest") Guest guest);
    Guest findGuest(@Param("id") long guestId);
    Collection<Guest> getRandomPassengers(@Param("limit") int limit);
}
