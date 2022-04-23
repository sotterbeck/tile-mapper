package de.simbuildings.tilemapper.ui.imagesplitting;

import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;
import de.simbuildings.tilemapper.tile.ImageSplitter;
import de.simbuildings.tilemapper.tile.TileGrid;
import de.simbuildings.tilemapper.tile.TilePropertiesWriter;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileModel implements Exportable {
    private final ObjectProperty<BufferedImage> originalImage = new SimpleObjectProperty<>();
    private final StringProperty fileLabelText = new SimpleStringProperty();

    private final ObservableList<Integer> validTargetResolutions = FXCollections.observableArrayList();

    private final IntegerProperty targetResolution = new SimpleIntegerProperty();
    private final StringProperty blockName = new SimpleStringProperty();

    private final ObjectProperty<TileGrid> tileGrid = new SimpleObjectProperty<>();

    @Inject
    public TileModel() {
        bindValidResolutions();
        bindTileGrid();
    }

    private void bindTileGrid() {
        targetResolution.addListener((observable, newTargetResolution, oldTargetResolution) -> {
            SquareImageResolution squareTargetResolution = new SquareImageResolution(this.getTargetResolution());
            if (squareTargetResolution.getHeight() == 0) {
                return;
            }
            tileGrid.set(new TileGrid(new ImageResolution(originalImage.get()), squareTargetResolution));
        });
    }

    private void bindValidResolutions() {
        originalImage.addListener(((observable, oldImage, newImage) -> {
            Integer[] validResolutions = new ImageResolution(getOriginalImage()).getValidTextureResolutions().stream()
                    .map(ImageResolution::getHeight)
                    .filter(resolution -> resolution > 4)
                    .toArray(Integer[]::new);

            validTargetResolutions.setAll(validResolutions);
        }));
    }

    private ImageSplitter getSplitImageSplitter() {
        ImageSplitter imageSplitter = new ImageSplitter(originalImage.get(), new SquareImageResolution(targetResolution.get()));
        imageSplitter.split();
        return imageSplitter;
    }

    private TilePropertiesWriter getTilePropertiesWriter() {
        return new TilePropertiesWriter(tileGrid.get(), blockName.get());
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

    public ObjectProperty<TileGrid> tileGridProperty() {
        return tileGrid;
    }

    public BufferedImage getOriginalImage() {
        return originalImage.get();
    }

    public void setOriginalImage(File originalImageFile) throws IOException, IllegalArgumentException {
        BufferedImage image = ImageIO.read(originalImageFile);
        ImageResolution resolution = new ImageResolution(image);
        if (!resolution.isPowerOfTwo()) {
            fileLabelText.set("Image resolution is not power of two");
            return;
        }
        this.fileLabelText.set(originalImageFile.getName());
        this.originalImage.set(image);
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

    @Override
    public void export(File destination) throws IOException {
        getSplitImageSplitter().export(destination);
        getTilePropertiesWriter().export(destination);
    }

    @Override
    public boolean outputExists(File destinationDirectory) {
        return (getSplitImageSplitter().outputExists(destinationDirectory) || getTilePropertiesWriter().outputExists(destinationDirectory));
    }
}