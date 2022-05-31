package de.simbuildings.tilemapper.ui.imagesplitting;

import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.ctm.CtmExporter;
import de.simbuildings.tilemapper.ctm.RepeatCtmExporter;
import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;
import de.simbuildings.tilemapper.tile.TileGrid;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class TileModel implements Exportable {
    private final ObjectProperty<BufferedImage> originalImage = new SimpleObjectProperty<>();
    private final StringProperty fileLabelText = new SimpleStringProperty();

    private final ObservableList<Integer> validTargetResolutions = FXCollections.observableArrayList();

    private final IntegerProperty targetResolution = new SimpleIntegerProperty();
    private final StringProperty blockName = new SimpleStringProperty();

    private final ObjectProperty<TileGrid> tileGrid = new SimpleObjectProperty<>();

    @Inject
    public TileModel() {
        originalImage.addListener(this::updateValidTargetResolutions);
        targetResolution.addListener(this::updateGrid);
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

    public void setFileLabelText(String fileLabelText) {
        this.fileLabelText.set(fileLabelText);
    }

    @Override
    public void export(Path destination) throws IOException {
        getCtmExporter().export(destination);
    }

    @Override
    public boolean hasConflict(Path destinationDirectory) {
        return getCtmExporter().hasConflict(destinationDirectory);
    }

    @Override
    public Set<Path> conflictFiles(Path destinationDirectory) {
        return getCtmExporter().conflictFiles(destinationDirectory);
    }

    public CtmExporter getCtmExporter() {
        SquareImageResolution squareTargetResolution = new SquareImageResolution(targetResolution.get());
        return RepeatCtmExporter.of(originalImage.get(), squareTargetResolution, blockName.get());
    }

    private void updateGrid(ObservableValue<? extends Number> observable, Number newTargetResolution, Number oldTargetResolution) {
        SquareImageResolution squareTargetResolution = new SquareImageResolution(this.getTargetResolution());
        if (squareTargetResolution.getHeight() == 0) {
            return;
        }
        tileGrid.set(new TileGrid(new ImageResolution(originalImage.get()), squareTargetResolution));
    }

    private void updateValidTargetResolutions(ObservableValue<? extends BufferedImage> observable, BufferedImage oldImage, BufferedImage newImage) {
        List<Integer> validResolutions = new ImageResolution(getOriginalImage()).getValidTextureResolutions().stream()
                .map(ImageResolution::getHeight)
                .filter(resolution -> resolution > 4)
                .toList();

        validTargetResolutions.setAll(validResolutions);
    }
}