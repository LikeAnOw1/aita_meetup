package me.likeanowl.aitameetup.images;

import java.awt.image.RGBImageFilter;

public class WhiteBackgroundFilter extends RGBImageFilter {
    private static final int WHITE = 0xFFFFFFFF;
    @Override
    public final int filterRGB(final int x, final int y, final int rgb) {
        if ((rgb | 0xFF000000) == WHITE) {
            return 0x00FFFFFF & rgb;
        } else {
            return rgb;
        }
    }
}
