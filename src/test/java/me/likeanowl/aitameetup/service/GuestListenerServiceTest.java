package me.likeanowl.aitameetup.service;

import me.likeanowl.aitameetup.config.ApplicationProperties;
import me.likeanowl.aitameetup.model.Guest;
import me.likeanowl.aitameetup.repository.GuestMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doReturn;

@ContextConfiguration(classes = {GuestListenerServiceTest.ApplicationPropertiesConfig.class})
@ExtendWith(SpringExtension.class)
class GuestListenerServiceTest {

    @MockBean
    private GuestMapper guestMapper;

    @Autowired
    private GuestListenerService guestListenerService;

    private final List<Guest> randomGuests = List.of(
            new Guest(1, "first", "first", 500, 2),
            new Guest(2, "second", "second", 1000, 3),
            new Guest(3, "third", "third", 250, 2),
            new Guest(4, "fourth", "fourth", 100, 1)
    );

    @Test
    void testRandomGuestListeners() {
        doReturn(randomGuests).when(guestMapper).getRandomGuests(anyInt());

        var firstListener = Flux.create(guestListenerService::registerListener);
        var secondListener = Flux.create(guestListenerService::registerListener);

        var first = getVerifier(firstListener);
        var second = getVerifier(secondListener);

        first.verify();
        second.verify();
    }

    private StepVerifier getVerifier(Flux<Guest> listener) {
        return StepVerifier.create(listener)
                .expectSubscription()
                .assertNext(guest -> assertEquals(randomGuests.get(0), guest))
                .assertNext(guest -> assertEquals(randomGuests.get(1), guest))
                .assertNext(guest -> assertEquals(randomGuests.get(2), guest))
                .assertNext(guest -> assertEquals(randomGuests.get(3), guest))
                .thenCancel()
                .verifyLater();
    }

    @MockBean(GuestMapper.class)
    @TestConfiguration
    public static class ApplicationPropertiesConfig {
        @Bean
        public ApplicationProperties applicationProperties() {
            var properties = new ApplicationProperties();
            var random = new ApplicationProperties.RandomGuest();
            random.setQueueSize(5);
            random.setReloadThreshold(1);
            random.setReloadInterval(1);
            properties.setRandomGuest(random);
            return properties;
        }

        @Bean
        public GuestListenerService guestListenerService(GuestMapper mapper, ApplicationProperties properties) {
            return new GuestListenerService(mapper, properties);
        }
    }

}