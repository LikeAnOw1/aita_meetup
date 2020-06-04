package me.likeanowl.aitameetup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.likeanowl.aitameetup.model.Guest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckinListenerService extends ListenableService<Guest> {

    private static final String SERVICE_NAME = "checkIn";

    private final BoardingPassService boardingPassService;
    private volatile Guest lastCheckedInGuest;

    @PostConstruct
    private void setup() {
        this.lastCheckedInGuest = boardingPassService.getLastCheckedInGuest();
    }

    @Override
    protected Guest getInitialResource() {
        return lastCheckedInGuest;
    }

    @Override
    protected String serviceName() {
        return SERVICE_NAME;
    }

    public Guest checkIn(String invitationCode) {
        var guest = boardingPassService.checkIn(invitationCode);
        notifyAllListeners(guest);
        this.lastCheckedInGuest = guest;
        return guest;
    }

}
