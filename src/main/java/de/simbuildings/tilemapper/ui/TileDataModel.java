package de.simbuildings.tilemapper.ui;

import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;
import de.simbuildings.tilemapper.tile.ImageSplitter;
import de.simbuildings.tilemapper.tile.TilePropertiesWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by SimBuildings on 01.11.21 at 12:14
 */
class TileDataModel {
    private final ImageSplitter splitter = new ImageSplitter();
    private TilePropertiesWriter propertiesWriter;
    private File imageFile;
    private File directory;

    private String block;

    // TODO: isValid method to enable/disable Form
    public void setOriginalImage(File imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        if (!new ImageResolution(image).isPowerOfTwo())
            throw new IllegalArgumentException("wrong image size");
        this.imageFile = imageFile;
        this.directory = imageFile.getParentFile();

        splitter.setOriginalImage(image);
    }

    public void setTargetResolution(SquareImageResolution resolution) {
        splitter.setTargetResolution(resolution);
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public void split() {
        splitter.split();
    }

    public void export() {
        String directoryPath = getDirectory().getAbsolutePath() + "/";
        propertiesWriter = new TilePropertiesWriter(splitter.getTileGrid(), block);
        splitter.save(directoryPath);
        propertiesWriter.write(directoryPath);
    }

    public void export(File directory) {
        setDirectory(directory);
        export();
    }

    public int getGridHeight() {
        return splitter.getTileGrid().getHeight();
    }

    public int getGridWidth() {
        return splitter.getTileGrid().getWidth();
    }

    // TODO: make it possible to change export directory
    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }

    public String getFileName() {
        return imageFile.getName();
    }

    public File getImageFile() {
        return imageFile;
    }

    public ImageResolution getOriginalResolution() {
        return splitter.getOriginalResolution();
    }

}
