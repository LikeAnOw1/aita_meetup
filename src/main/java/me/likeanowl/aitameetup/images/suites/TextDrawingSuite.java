package me.likeanowl.aitameetup.images.suites;

import lombok.Data;

import java.awt.*;

@Data
public class TextDrawingSuite implements DrawingSuite<String> {
    private String name;
    private int x;
    private int y;
    private Color color;
    private Font font;

    @Override
    public void draw(Graphics2D graphics, String resource) {
        graphics.setPaint(color.toAwtColor());
        graphics.setFont(font.toAwtFont());
        graphics.drawString(resource, x, y);
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
