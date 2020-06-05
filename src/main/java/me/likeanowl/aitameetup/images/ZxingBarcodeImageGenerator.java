package me.likeanowl.aitameetup.images;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.pdf417.PDF417Writer;
import lombok.SneakyThrows;
import me.likeanowl.aitameetup.config.ImagesProperties;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;

@Component
public class ZxingBarcodeImageGenerator implements BarcodeImageGenerator {
    private final Writer barcodeWriter = new PDF417Writer();
    private final ImageFilter whiteBackgroundFilter = new WhiteBackgroundFilter();

    private final ImagesProperties.Barcode properties;

    public ZxingBarcodeImageGenerator(ImagesProperties properties) {
        this.properties = properties.getBarcode();
    }

    @SneakyThrows
    public Image buildBarcodeImage(String content) {
        var encoded = barcodeWriter.encode(content, BarcodeFormat.PDF_417,
                properties.getWidth(), properties.getHeight());

        var image = MatrixToImageWriter.toBufferedImage(encoded);
        var source = new FilteredImageSource(image.getSource(), whiteBackgroundFilter);
        return Toolkit.getDefaultToolkit().createImage(source);
    }

}
