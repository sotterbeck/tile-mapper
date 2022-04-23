package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.common.Exportable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

record Tile(BufferedImage image, int tileId) implements Exportable {

    public void export(File destinationDirectory) throws IOException {
        File outputFile = getOutputFile(destinationDirectory);

        ImageIO.write(image, "png", outputFile);
    }

    @Override
    public boolean outputExists(File destinationDirectory) {
        return getOutputFile(destinationDirectory).exists();
    }

    private File getOutputFile(File destinationDirectory) {
        return new File(destinationDirectory, String.format("%d.png", tileId));
    }
}
