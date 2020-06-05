package me.likeanowl.aitameetup.images.suites;

import lombok.Data;

import java.awt.*;

@Data
public class ImageDrawingSuite implements DrawingSuite<Image> {
    private String name;
    private int x;
    private int y;

    @Override
    public void draw(Graphics2D graphics, Image resource) {
        graphics.drawImage(resource, x, y, null);
    }
}
