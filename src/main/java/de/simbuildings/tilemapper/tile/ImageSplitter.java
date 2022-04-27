package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImageSplitter implements Exportable {
    private BufferedImage originalImage;

    private ImageResolution originalResolution;
    private ImageResolution targetResolution;
    private TileGrid tileGrid;

    private Tile[] tiles;

    private ImageSplitter(BufferedImage originalImage, SquareImageResolution targetResolution) {
        setOriginalImage(originalImage);
        setTargetResolution(targetResolution);
    }

    public static ImageSplitter of(BufferedImage originalImage, SquareImageResolution targetResolution) {
        return new ImageSplitter(originalImage, targetResolution);
    }

    private void split() {
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
        split();
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
        try (Stream<Path> pathStream = Files.list(destinationDirectory)) {
            Set<Path> outputPaths = getOutputPaths(destinationDirectory);
            return pathStream
                    .filter(outputPaths::contains)
                    .collect(Collectors.toUnmodifiableSet());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Set<Path> getOutputPaths(Path destinationDirectory) {
        return Arrays.stream(tiles)
                .map(tile -> tile.getOutputPath(destinationDirectory))
                .collect(Collectors.toUnmodifiableSet());
    }

    Tile[] getTiles() {
        return tiles;   // TODO: use list or set
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