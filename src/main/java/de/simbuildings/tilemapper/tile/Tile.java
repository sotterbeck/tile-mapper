package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.common.Exportable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by SimBuildings on 10.10.21 at 17:20
 */
public class Tile implements Exportable {
    private final BufferedImage subImage;
    private final int tileID;

    public Tile(BufferedImage subImage, int tileID) {
        this.subImage = subImage;
        this.tileID = tileID;
    }

    public int getTileID() {
        return tileID;
    }

    public void export(File destinationDirectory) throws IOException, IllegalArgumentException {
        File outputFile = getOutputFile(destinationDirectory);

        ImageIO.write(subImage, "png", outputFile);
    }

    @Override
    public boolean outputExists(File destinationDirectory) {
        return getOutputFile(destinationDirectory).exists();
    }

    private File getOutputFile(File destinationDirectory) {
        return new File(destinationDirectory, String.format("%d.png", tileID));
    }

    public BufferedImage getSubImage() {
        return subImage;
    }
}
