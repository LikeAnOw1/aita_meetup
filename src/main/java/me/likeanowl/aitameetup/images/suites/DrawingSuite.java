package me.likeanowl.aitameetup.images.suites;

import java.awt.*;

public interface DrawingSuite<T> {
    void draw(Graphics2D graphics, T resource);
}
