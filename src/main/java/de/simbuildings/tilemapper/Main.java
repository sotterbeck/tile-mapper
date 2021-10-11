package de.simbuildings.tilemapper;

import de.simbuildings.tilemapper.image.SquareImageResolution;
import de.simbuildings.tilemapper.tile.ImageSpliter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by SimBuildings on 09.10.21 at 23:44
 */
public class Main {
    public static void main(String[] args) {
        // TODO: turn this test code into a unit test
        try {
            BufferedImage originalImage = ImageIO.read(new File("/Users/simon/Documents/Development/Java/Test/tile_sample.png"));
            ImageSpliter imageSpliter = new ImageSpliter(originalImage, new SquareImageResolution(64));
            imageSpliter.split();
            imageSpliter.exportTiles("/Users/simon/Documents/Development/Java/Test/tile_sample_split/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
