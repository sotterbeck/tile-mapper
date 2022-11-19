package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;

public class TileGrid {
    private final int height;
    private final int width;

    public TileGrid(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public TileGrid(ImageResolution originalResolution, SquareImageResolution targetResolution) {
        if (targetResolution.isLargerThan(originalResolution)) {
            throw new IllegalArgumentException("target resolution is larger than original resolution");
        }
        this.height = originalResolution.getHeight() / targetResolution.getHeight();
        this.width = originalResolution.getWidth() / targetResolution.getWidth();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getTileAmount() {
        return width * height;
    }
}
