package me.likeanowl.aitameetup.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("aita-meetup")
public class ApplicationProperties {
    private RandomGuest randomGuest;
    private int batchSize;

    @Data
    public static class RandomGuest {
        private int queueSize;
        private int reloadInterval;
        private int reloadThreshold;
    }
}
