package me.likeanowl.aitameetup.config;

import me.likeanowl.aitameetup.errors.NetworkException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> attributes = super.getErrorAttributes(request, options);

        var error = getError(request);
        attributes.put("exception", error.getClass().getSimpleName());
        attributes.put("message", error.getMessage());
        if (error instanceof NetworkException) {
            var ex = (NetworkException) getError(request);
            attributes.put("status", ex.getStatus().value());
            attributes.put("error", ex.getStatus().getReasonPhrase());
        }

        return attributes;
    }
}
