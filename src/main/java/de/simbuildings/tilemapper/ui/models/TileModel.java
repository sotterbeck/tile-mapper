package de.simbuildings.tilemapper.ui.models;

import javafx.beans.property.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by SimBuildings on 12.02.22 at 21:41
 */
public class TileModel {
    private final IntegerProperty targetResolution = new SimpleIntegerProperty();
    private final StringProperty blockName = new SimpleStringProperty();
    private final ObjectProperty<BufferedImage> originalImage = new SimpleObjectProperty<>();

    public IntegerProperty targetResolutionProperty() {
        return targetResolution;
    }

    public StringProperty blockNameProperty() {
        return blockName;
    }

    public ObjectProperty<BufferedImage> originalImageProperty() {
        return originalImage;
    }

    public void setOriginalImage(BufferedImage originalImage) {
        this.originalImage.set(originalImage);
    }

    public void setOriginalImage(File originalImageFile) throws IOException {
        BufferedImage image = ImageIO.read(originalImageFile);
        this.originalImage.set(image);
    }
}
