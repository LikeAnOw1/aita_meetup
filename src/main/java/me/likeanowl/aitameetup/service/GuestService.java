package me.likeanowl.aitameetup.service;

import lombok.RequiredArgsConstructor;
import me.likeanowl.aitameetup.repository.GuestMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestMapper guestMapper;
}
