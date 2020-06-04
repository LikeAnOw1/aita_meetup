package me.likeanowl.aitameetup.images;

import lombok.SneakyThrows;
import me.likeanowl.aitameetup.config.ImagesProperties;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class BoardingPassImageGenerator {

    private final ImagesProperties.BoardingPass properties;
    private final BufferedImage template;

    @SneakyThrows
    public BoardingPassImageGenerator(ImagesProperties properties) {
        this.properties = properties.getBoardingPass();
        this.template = ImageIO.read(Files.newInputStream(Path.of(this.properties.getTemplate())));
    }

    @SneakyThrows
    public byte[] buildBoardingPassImage() {
        var image = new BufferedImage(template.getWidth(), template.getHeight(), template.getType());
        var graphics = image.createGraphics();
        try {
            setRenderingQuality(graphics);
            graphics.drawImage(template, 0, 0, null);
            graphics.setPaint(Color.BLACK);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 40));
            graphics.drawString("Test string", 100, 50);
            var output = new ByteArrayOutputStream();
            ImageIO.write(image, "png", output);
            return output.toByteArray();
        } finally {
            graphics.dispose();
        }
    }

    private static void setRenderingQuality(Graphics2D graphics) {
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }
}
