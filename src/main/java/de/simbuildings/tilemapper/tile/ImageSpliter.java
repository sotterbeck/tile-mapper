package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.image.ImageResolution;

import java.awt.image.BufferedImage;

/**
 * Created by SimBuildings on 10.10.21 at 17:08
 */
public class ImageSpliter {
    private final BufferedImage originalImage;
    private final ImageResolution originalResolution;
    private final ImageResolution targetResolution;

    int tileGrid;

    private Tile[] tiles;

    public ImageSpliter(BufferedImage originalImage, ImageResolution targetResolution) {
        this.originalImage = originalImage;
        this.targetResolution = targetResolution;

        this.originalResolution = new ImageResolution(
                this.originalImage.getWidth(),
                this.originalImage.getHeight()
        );

        tileGrid = originalResolution.toSquaredResolution() / targetResolution.toSquaredResolution();

        // TODO: use setters to initiate object and check if resolutions are valid
    }

    public void split() {
        if (!isValid()) {
            return;
        }

        tiles = new Tile[tileGrid * tileGrid];

        int id = 1;
        for (int y = 0; y < tileGrid; y++) {
            System.out.println(id);
            id ++;
            // horizontal tiles
            for (int x = 0; x < tileGrid - 1; x++) {
                System.out.println(id);
                tiles[id] = new Tile(
                        originalImage.getSubimage(targetResolution.getWidth() * x, targetResolution.getHeight() * y, targetResolution.getWidth(), targetResolution.getHeight()),
                        targetResolution,
                        id
                );
                id ++;
            }
        }

        // TODO: loop and add subimages to tile array with ids from 0 - tileGrid^2 for optifine
    }

    public void exportTiles(String destDir) {
        // TODO: run export method on all tiles
        for (Tile tile:
             tiles) {
            tile.export(destDir);
        }
    }

    private boolean isValid() {
        return originalResolution.isRectangular() && originalResolution.isPowerOfTwo() &&
                targetResolution.isRectangular() && originalResolution.isPowerOfTwo();
        // TODO: Change this! ...
    }

}
