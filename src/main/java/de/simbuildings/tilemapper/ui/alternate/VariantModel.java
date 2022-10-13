package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.variations.Variant;
import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

class VariantModel {
    private final StringProperty name;
    private final IntegerProperty weight;
    private final ObjectProperty<Image> image;

    VariantModel(Variant variant) {
        name = new SimpleStringProperty(variant.defaultTexture().name());
        weight = new SimpleIntegerProperty(variant.weight());
        image = new SimpleObjectProperty<>(getImage(variant));
    }

    private Image getImage(Variant variant) {
        Image imageFromPath;
        Path imagePath = variant.defaultTexture().file();
        try {
            FileInputStream fileInputStream = new FileInputStream(imagePath.toFile());
            imageFromPath = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
        return imageFromPath;
    }

    static VariantModel fromDto(Variant variant) {
        return new VariantModel(variant);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty weightProperty() {
        return weight;
    }
}
