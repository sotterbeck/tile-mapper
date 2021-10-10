package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.image.ImageResolution;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by SimBuildings on 10.10.21 at 17:20
 */
public class Tile {
    private BufferedImage subImage;
    private int tileID;

    public Tile(BufferedImage subImage, ImageResolution tileResolution, int tileID) {
        this.subImage = subImage;
        this.tileID = tileID;
    }

    public void export(String destDir) {
        File outputFile = new File(destDir + tileID);
        try {
            ImageIO.write(subImage, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSubImage() {
        return subImage;
    }
}
