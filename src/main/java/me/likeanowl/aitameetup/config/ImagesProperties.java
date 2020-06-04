package me.likeanowl.aitameetup.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@Data
@ConfigurationProperties("images")
public class ImagesProperties {
    private BoardingPass boardingPass;
    private Barcode barcode;

    @Data
    public static class BoardingPass {
        private int width;
        private int height;
        private URI template;
    }

    @Data
    public static class Barcode {
        private int width;
        private int height;
    }
}
