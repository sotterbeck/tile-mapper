package de.simbuildings.tilemapper.image;

/**
 * Created by SimBuildings on 10.10.21 at 17:10
 */
public class ImageResolution {
    private int width;
    private int height;

    public ImageResolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public ImageResolution(int squaredResolution) {
        this(squaredResolution, squaredResolution);
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isRectangular() {
        return width == height;
    }

    public boolean isPowerOfTwo() {
        if (isRectangular()) {
            return (width & (width - 1)) == 0;
        }

        return false;
    }

    public int toSquaredResolution() {
        return height;
    }
}
