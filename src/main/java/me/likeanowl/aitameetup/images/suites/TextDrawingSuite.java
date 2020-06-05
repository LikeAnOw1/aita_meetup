package me.likeanowl.aitameetup.images.suites;

import lombok.Data;
import org.apache.commons.text.WordUtils;

import java.awt.*;

@Data
public class TextDrawingSuite implements DrawingSuite<String> {
    private String name;
    private int x;
    private int y;
    private Color color;
    private Font font;
    private int wrapLength;

    @Override
    public void draw(Graphics2D graphics, String resource) {
        graphics.setPaint(color.toAwtColor());
        graphics.setFont(font.toAwtFont());
        var wrapped = wrapLength > 0
                ? WordUtils.wrap(resource, wrapLength, "\n", true)
                : resource;

        var tokens = wrapped.split("\n");
        var localY = y;
        for (var token : tokens) {
            graphics.drawString(token, x, localY);
            localY += font.fontSize + 2;
        }
    }

    @Data
    public static class Font {
        private String name;
        private int style;
        private int fontSize;

        public java.awt.Font toAwtFont() {
            return new java.awt.Font(name, style, fontSize);
        }
    }

    @Data
    public static class Color {
        private int r;
        private int g;
        private int b;

        public java.awt.Color toAwtColor() {
            return new java.awt.Color(r, g, b);
        }
    }
}
