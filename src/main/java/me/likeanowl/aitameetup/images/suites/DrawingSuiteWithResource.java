package me.likeanowl.aitameetup.images.suites;

import lombok.Value;

import java.awt.*;

@Value
public class DrawingSuiteWithResource<T> {
    DrawingSuite<T> drawingSuite;
    T resource;

    public void draw(Graphics2D graphics) {
        if (resource != null) {
            drawingSuite.draw(graphics, resource);
        }
    }
}
