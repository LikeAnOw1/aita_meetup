package me.likeanowl.aitameetup.config;

import lombok.Data;
import me.likeanowl.aitameetup.images.suites.ImageDrawingSuite;
import me.likeanowl.aitameetup.images.suites.TextDrawingSuite;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.util.List;

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
        private List<TextDrawingSuite> textDrawingSuites;
        private List<ImageDrawingSuite> barcodeDrawingSuites;
    }

    @Data
    public static class Barcode {
        private int width;
        private int height;
    }
}
