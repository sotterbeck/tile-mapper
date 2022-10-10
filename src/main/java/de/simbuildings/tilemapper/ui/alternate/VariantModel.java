package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.variations.VariantDto;
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

    VariantModel(VariantDto variantDto) {
        name = new SimpleStringProperty(variantDto.defaultTexture().name());
        weight = new SimpleIntegerProperty(variantDto.weight());
        image = new SimpleObjectProperty<>(getImage(variantDto));
    }

    private Image getImage(VariantDto variantDto) {
        Image imageFromPath;
        Path imagePath = variantDto.defaultTexture().file();
        try {
            FileInputStream fileInputStream = new FileInputStream(imagePath.toFile());
            imageFromPath = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
        return imageFromPath;
    }

    static VariantModel fromDto(VariantDto variantDto) {
        return new VariantModel(variantDto);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty weightProperty() {
        return weight;
    }
}
