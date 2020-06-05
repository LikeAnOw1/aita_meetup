package me.likeanowl.aitameetup.repository;

import me.likeanowl.aitameetup.model.Guest;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface GuestMapper {
    void insertGuests(@Param("guests") Collection<Guest> guest);
    Guest findGuest(@Param("id") long guestId);
    Collection<Guest> getRandomGuests(@Param("limit") int limit);
}
