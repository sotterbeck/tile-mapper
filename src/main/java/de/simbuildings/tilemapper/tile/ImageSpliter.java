package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;

import java.awt.image.BufferedImage;

/**
 * Created by SimBuildings on 10.10.21 at 17:08
 */
public class ImageSpliter {
    private final BufferedImage originalImage;
    private final ImageResolution originalResolution;
    private final ImageResolution targetResolution;

    private final TileGrid tileGrid;
    private Tile[] tiles;

    public ImageSpliter(BufferedImage originalImage, SquareImageResolution targetResolution) {
        this.originalImage = originalImage;
        // resolutions
        this.targetResolution = targetResolution;
        this.originalResolution = new ImageResolution(this.originalImage);
        // TODO implement checks in TileGrid
        if (!(originalResolution.isPowerOfTwo() && targetResolution.isPowerOfTwo())) {
            throw new RuntimeException("resolutions must be a multiple of two");
        }
        this.tileGrid = new TileGrid(originalResolution, targetResolution);
    }

    public void split() {
        tiles = new Tile[tileGrid.getTileAmout()];

        int tileId = 0;
        for (int y = 0; y < tileGrid.getHeight(); y++) {
            for (int x = 0; x < tileGrid.getWidth(); x++) {
                tiles[tileId] = new Tile(originalImage.getSubimage(
                        x * targetResolution.getWidth(),
                        y * targetResolution.getHeight(),
                        targetResolution.getWidth(), targetResolution.getHeight()), tileId
                );
                tileId++;
            }
        }
    }

    public void exportTiles(String destDir) {
        for (Tile tile:
             tiles) {
            tile.export(destDir);
        }
    }

    public Tile[] getTiles() {
        return tiles;
    }
}
