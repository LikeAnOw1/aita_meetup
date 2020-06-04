package me.likeanowl.aitameetup.config;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//@Component
//@Order(-2)
public class ApplicationWebExceptionHandler implements ErrorWebExceptionHandler {

    @Nonnull
    @Override
    public Mono<Void> handle(@Nullable ServerWebExchange exchange, @Nonnull Throwable ex) {
        return Mono.empty();
    }
}
