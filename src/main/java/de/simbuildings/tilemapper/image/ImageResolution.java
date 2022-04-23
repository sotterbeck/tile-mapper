package de.simbuildings.tilemapper.image;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public boolean isSquare() {
        return width == height;
    }

    public boolean isPowerOfTwo() {
        return valueIsPowerOfTwo(getHeight()) && valueIsPowerOfTwo(getWidth());
    }

    public boolean isLargerThan(ImageResolution other) {
        return (width > other.width
                && height > other.height);
    }

    public List<SquareImageResolution> getValidTextureResolutions() {
        return Stream.iterate(2, res -> res < height || res < width, res -> res * 2)
                .map(SquareImageResolution::new)
                .collect(Collectors.toList());
    }

    private boolean valueIsPowerOfTwo(int value) {
        return (value & (value - 1)) == 0;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Returns the string representation of an image resolution.
     * The string contains the width and height of the resolution divided by an "x" symbol.
     * A resolution with a width of 256 and height of 512 would output as "256x512".
     */
    @Override
    public String toString() {
        return width + "x" + height;
    }
}
