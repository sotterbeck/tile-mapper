package de.simbuildings.tilemapper.ui.models;

import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by SimBuildings on 12.02.22 at 21:41
 */
public class TileModel {
    private final ObjectProperty<BufferedImage> originalImage = new SimpleObjectProperty<>();
    private final StringProperty fileLabelText = new SimpleStringProperty();

    private final ObservableList<Integer> validTargetResolutions = FXCollections.observableArrayList();

    private final IntegerProperty targetResolution = new SimpleIntegerProperty();
    private final StringProperty blockName = new SimpleStringProperty();

    public TileModel() {
        validTargetResolutionsListener();
    }

    private void validTargetResolutionsListener() {
        originalImage.addListener(((observable, oldImage, newImage) -> {
            validTargetResolutions.clear();

            List<SquareImageResolution> valuesPowerOfTwoUntilRes = new ImageResolution(newImage).getValuesPowerOfTwoUntilRes();
            valuesPowerOfTwoUntilRes.forEach(squareImageResolution -> validTargetResolutions.add(squareImageResolution.getHeight()));
        }));
    }

    public ObjectProperty<BufferedImage> originalImageProperty() {
        return originalImage;
    }

    public StringProperty fileLabelTextProperty() {
        return fileLabelText;
    }

    public IntegerProperty targetResolutionProperty() {
        return targetResolution;
    }

    public ObservableList<Integer> validTargetResolutionsProperty() {
        return validTargetResolutions;
    }

    public StringProperty blockNameProperty() {
        return blockName;
    }

    public BufferedImage getOriginalImage() {
        return originalImage.get();
    }

    public int getTargetResolution() {
        return targetResolution.get();
    }

    public String getBlockName() {
        return blockNameProperty().get();
    }

    public void setFileLabelText(String fileLabelText) {
        this.fileLabelText.set(fileLabelText);
    }

    public void setOriginalImage(BufferedImage originalImage) throws IllegalArgumentException {
        validateImage(originalImage);
        this.originalImage.set(originalImage);
    }

    public void setOriginalImage(File originalImageFile) throws IOException, IllegalArgumentException {
        BufferedImage image = ImageIO.read(originalImageFile);
        validateImage(image);

        this.fileLabelText.set(originalImageFile.getName());
        this.originalImage.set(image);
    }

    private void validateImage(BufferedImage image) {
        if (!new ImageResolution(image).isPowerOfTwo()) {
            originalImage.set(null);
            throw new IllegalArgumentException("image is not power of two");
        }
    }
}
