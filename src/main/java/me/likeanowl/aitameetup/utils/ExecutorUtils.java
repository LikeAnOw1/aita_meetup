package me.likeanowl.aitameetup.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.TimeUnit.MINUTES;

@Slf4j
@UtilityClass
public class ExecutorUtils {
    public void shutdown(ExecutorService executor) {
        try {
            executor.shutdown();
            boolean success = executor.awaitTermination(1, MINUTES);
            if (!success) {
                log.error("Could not shutdown executor! Waited 1 min");
            }
        } catch (InterruptedException e) {
            log.error("Error shutting down executor", e);
            Thread.currentThread().interrupt();
        }
    }
}
