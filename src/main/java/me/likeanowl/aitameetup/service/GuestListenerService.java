package me.likeanowl.aitameetup.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.likeanowl.aitameetup.config.ApplicationProperties;
import me.likeanowl.aitameetup.model.Guest;
import me.likeanowl.aitameetup.repository.GuestMapper;
import me.likeanowl.aitameetup.utils.ExecutorUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class GuestListenerService extends ListenableService<Guest> {
    public static final String SERVICE_NAME = "Guests";

    private final Queue<Guest> randomGuestQueue = new ConcurrentLinkedQueue<>();
    private final ScheduledExecutorService guestWorker = Executors.newSingleThreadScheduledExecutor();

    private final GuestMapper guestMapper;
    private final ApplicationProperties.RandomGuest properties;
    private final int batchSize;

    private volatile Guest lastPolledGuest;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public GuestListenerService(GuestMapper guestMapper,
                                ApplicationProperties properties) {
        this.guestMapper = guestMapper;
        this.properties = properties.getRandomGuest();
        this.batchSize = properties.getBatchSize();
    }

    @PostConstruct
    private void setup() {
        fillRandomGuestQueue();
        lastPolledGuest = randomGuestQueue.poll();
        guestWorker.scheduleAtFixedRate(this::sendRandomGuest,
                0L, properties.getReloadInterval(), TimeUnit.SECONDS);
    }

    public void insertGuests(List<Guest> guests) {
        Lists.partition(guests, batchSize).forEach(guestMapper::insertGuests);
    }

    @Override
    protected Guest getInitialResource() {
        return lastPolledGuest;
    }

    @Override
    protected String serviceName() {
        return SERVICE_NAME;
    }

    private void sendRandomGuest() {
        if (currentListeners() == 0) {
            return;
        }

        if (randomGuestQueue.size() <= properties.getReloadThreshold()) {
            fillRandomGuestQueue();
        }

        log.debug("Polling random guest..");
        var guest = randomGuestQueue.poll();
        lastPolledGuest = guest;
        notifyAllListeners(guest);
    }

    private void fillRandomGuestQueue() {
        log.debug("Reloading random guest queue, current reload threshold is {}",
                properties.getReloadThreshold());
        var guests = guestMapper.getRandomGuests(properties.getQueueSize());
        randomGuestQueue.addAll(guests);
    }

    @PreDestroy
    protected void tearDown() {
        super.tearDown();
        ExecutorUtils.shutdown(guestWorker);
    }
}
