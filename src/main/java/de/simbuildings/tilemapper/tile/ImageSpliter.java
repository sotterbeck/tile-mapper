package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;

import java.awt.image.BufferedImage;

/**
 * Created by SimBuildings on 10.10.21 at 17:08
 */
public class ImageSpliter {
    private BufferedImage originalImage;

    private ImageResolution originalResolution;
    private ImageResolution targetResolution;
    private TileGrid tileGrid;

    private Tile[] tiles;

    public ImageSpliter() {}

    public ImageSpliter(BufferedImage originalImage, SquareImageResolution targetResolution) {
        setOriginalImage(originalImage);
        setTargetResolution(targetResolution);
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

    public void save(String destDir) {
        for (Tile tile :
                tiles) {
            tile.export(destDir);
        }
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public TileGrid getTileGrid() {
        return tileGrid;
    }

    public void setOriginalImage(BufferedImage originalImage) {
        this.originalImage = originalImage;
        this.originalResolution = new ImageResolution(originalImage);
        if (!originalResolution.isPowerOfTwo()) {
            throw new IllegalArgumentException("original image height and width must be multiple of two");
        }
    }

    public void setTargetResolution(SquareImageResolution targetResolution) {
        this.targetResolution = targetResolution;
        if (!targetResolution.isPowerOfTwo()) {
            throw new IllegalArgumentException("target image size must be multiple of two");
        }
        if (originalResolution == null) {
            throw new IllegalStateException("original image is not set");
        }

        this.tileGrid = new TileGrid(originalResolution, targetResolution);
    }

    public ImageResolution getOriginalResolution() {
        return originalResolution;
    }

    public ImageResolution getTargetResolution() {
        return targetResolution;
    }
}
