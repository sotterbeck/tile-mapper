package de.simbuildings.tilemapper.image;

import java.awt.image.BufferedImage;

/**
 * Created by SimBuildings on 10.10.21 at 17:10
 */
public class ImageResolution {
    private final int width;
    private final int height;

    public ImageResolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public ImageResolution(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isSquare() {
        return width == height;
    }

    public boolean isPowerOfTwo() {
        return valueIsPowerOfTwo(getHeight()) && valueIsPowerOfTwo(getWidth());
    }

    private static boolean valueIsPowerOfTwo(int size) {
        return (size & (size - 1)) == 0;
    }
}
