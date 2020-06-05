package me.likeanowl.aitameetup.images;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.likeanowl.aitameetup.config.ImagesProperties;
import me.likeanowl.aitameetup.images.suites.DrawingSuiteWithResource;
import me.likeanowl.aitameetup.model.BoardingPass;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class BoardingPassImageGenerator {

    private final BufferedImage template;
    private final DrawingSuiteService suiteService;

    @SneakyThrows
    public BoardingPassImageGenerator(ImagesProperties properties,
                                      DrawingSuiteService suiteService) {
        this.template = ImageIO.read(Files.newInputStream(Path.of(properties.getBoardingPass().getTemplate())));
        this.suiteService = suiteService;
    }

    public CompletableFuture<byte[]> buildBoardingPassImage(CompletableFuture<BoardingPass> boardingPass,
                                                            CompletableFuture<Image> barcodeImage) {
        var image = new BufferedImage(template.getWidth(), template.getHeight(), template.getType());
        var graphics = image.createGraphics();
        setRenderingQuality(graphics);
        graphics.drawImage(template, 0, 0, null);
        return boardingPass
                .thenCombine(barcodeImage, suiteService::buildDrawingSuites)
                .thenApply(suites -> drawImage(image, graphics, suites));
    }

    @SneakyThrows
    private byte[] drawImage(BufferedImage image, Graphics2D graphics, List<DrawingSuiteWithResource<?>> suites) {
        try {
            suites.forEach(suite -> suite.draw(graphics));
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
