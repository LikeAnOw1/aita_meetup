package me.likeanowl.aitameetup.service;

import lombok.extern.slf4j.Slf4j;
import me.likeanowl.aitameetup.utils.ExecutorUtils;
import reactor.core.Disposable;
import reactor.core.publisher.FluxSink;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
public abstract class ListenableService<T> {
    private final Map<UUID, Consumer<T>> listeners = new ConcurrentHashMap<>();
    private final ExecutorService listenerWorker = Executors.newFixedThreadPool(1);

    public void registerListener(FluxSink<T> listener) {
        var listenerId = UUID.randomUUID();
        log.debug("Registering checkIn listener with id: {}", listenerId);
        listener.onDispose(removeListener(listenerId));
        var initialResource = getInitialResource();
        if (initialResource != null) {
            listener.next(getInitialResource());
        }
        listeners.put(listenerId, listener::next);
    }

    protected abstract T getInitialResource();

    protected abstract String serviceName();

    protected void notifyAllListeners(T resource) {
        if (listeners.isEmpty()) {
            return;
        }

        log.debug("Sending resource info: {} to {} listeners", resource, listeners.size());
        listenerWorker.execute(() -> listeners.values().forEach(listener -> listener.accept(resource)));
    }

    protected Disposable removeListener(UUID listenerId) {
        return () -> {
            log.debug("Removing {} listener: {}", serviceName(), listenerId);
            listeners.remove(listenerId);
        };
    }

    protected int currentListeners() {
        return listeners.size();
    }

    @PreDestroy
    protected void tearDown() {
        ExecutorUtils.shutdown(listenerWorker);
    }
}
