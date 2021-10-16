package de.simbuildings.tilemapper.image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

    public ArrayList<SquareImageResolution> getValuesPowerOfTwoUntilRes() {
        ArrayList<SquareImageResolution> squareImageResolutions = new ArrayList<>();
        for (int i = 2; i < height || i < width; i = i * 2) {
            squareImageResolutions.add(new SquareImageResolution(i));
        }
        return squareImageResolutions;
    }

    private static boolean valueIsPowerOfTwo(int size) {
        return (size & (size - 1)) == 0;
    }
}
