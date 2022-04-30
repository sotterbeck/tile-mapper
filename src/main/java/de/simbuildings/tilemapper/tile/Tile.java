package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.common.Exportable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

public record Tile(BufferedImage image, int tileId) implements Exportable {
    @Override
    public void export(Path destinationDirectory) throws IOException {
        File outputFile = getOutputPath(destinationDirectory).toFile();

        ImageIO.write(image, "png", outputFile);
    }

    @Override
    public boolean hasConflict(Path destinationDirectory) {
        return Files.exists(getOutputPath(destinationDirectory));
    }

    @Override
    public Set<Path> getConflictFiles(Path destinationDirectory) {
        return Collections.emptySet();
    }

    Path getOutputPath(Path destinationDirectory) {
        return destinationDirectory.resolve(String.format("%d.png", tileId));
    }
}
