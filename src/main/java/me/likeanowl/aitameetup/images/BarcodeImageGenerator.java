package me.likeanowl.aitameetup.images;

import java.awt.*;

public interface BarcodeImageGenerator {
    Image buildBarcodeImage(String content);
}
