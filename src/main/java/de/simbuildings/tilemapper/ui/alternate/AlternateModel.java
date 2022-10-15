package de.simbuildings.tilemapper.ui.alternate;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.image.ImageResolution;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Collection;

public class AlternateModel {
    private final ObjectMapper objectMapper;

    private final StringProperty material = new SimpleStringProperty();
    private final ObservableList<VariantModel> variants = FXCollections.observableArrayList();
    private final ObjectProperty<VariantModel> selectedVariant = new SimpleObjectProperty<>();

    @Inject
    public AlternateModel(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private static ImageResolution getImageResolution(Path path) {
        try {
            return new ImageResolution(ImageIO.read(path.toFile()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public ObservableList<VariantModel> variants() {
        return FXCollections.unmodifiableObservableList(variants).sorted();
    }

    public ObjectProperty<VariantModel> selectedVariantProperty() {
        return selectedVariant;
    }

    public StringProperty materialProperty() {
        return material;
    }

    public void addVariants(Collection<Path> imagePaths) {
        imagePaths.stream()
                .map(VariantModel::new)
                .forEach(this::addVariantIfNotExists);
    }

    public boolean imagesPermitted(Collection<Path> imagePaths) {
        return imagePaths.stream()
                .map(AlternateModel::getImageResolution)
                .allMatch(imageResolution -> imageResolution.isPowerOfTwo()
                                             && imageResolution.isSquare());
    }

    private void addVariantIfNotExists(VariantModel newVariant) {
        boolean variantExists = variants.stream()
                .anyMatch(variantModel -> variantModel.nameProperty().get().equals(newVariant.nameProperty().get()));
        if (variantExists) {
            return;
        }
        variants.add(newVariant);
    }

    public void removeVariant(VariantModel variant) {
        variants.remove(variant);
    }
}
