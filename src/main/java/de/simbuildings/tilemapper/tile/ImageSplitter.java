package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;

public class ImageSplitter implements Exportable {
    private BufferedImage originalImage;

    private ImageResolution originalResolution;
    private ImageResolution targetResolution;
    private TileGrid tileGrid;

    private Tile[] tiles;

    public ImageSplitter(BufferedImage originalImage, SquareImageResolution targetResolution) {
        setOriginalImage(originalImage);
        setTargetResolution(targetResolution);
    }

    public void split() {
        tiles = new Tile[tileGrid.getTileAmount()];

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

    @Override
    public void export(Path destinationDirectory) throws IOException {
        for (Tile tile : tiles) {
            tile.export(destinationDirectory);
        }
    }

    @Override
    public boolean hasConflict(Path destinationDirectory) {
        return Arrays.stream(tiles)
                .anyMatch(tile -> tile.hasConflict(destinationDirectory));
    }

    @Override
    public Set<Path> getConflictFiles(Path destinationDirectory) {
        return null;
    }

    Tile[] getTiles() {
        return tiles;
    }

    private void setOriginalImage(BufferedImage originalImage) {
        ImageResolution resolution = new ImageResolution(originalImage);
        // TODO: validate if is dividable by valid target resolutions
        if (!resolution.isPowerOfTwo()) {
            throw new IllegalArgumentException("original image height and width must be multiple of two");
        }
        this.originalImage = originalImage;
        this.originalResolution = resolution;
    }

    private void setTargetResolution(SquareImageResolution targetResolution) {
        if (!targetResolution.isPowerOfTwo()) {
            throw new IllegalArgumentException("target image size must be multiple of two");
        }

        this.targetResolution = targetResolution;
        this.tileGrid = new TileGrid(originalResolution, targetResolution);
    }
}
