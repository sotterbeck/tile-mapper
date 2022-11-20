package de.simbuildings.tilemapper.gui.alternate;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Objects;

public final class VariantModel implements Comparable<VariantModel> {
    private final String name;
    private final Image image;
    private final IntegerProperty weight = new SimpleIntegerProperty();

    VariantModel(Path defaultTexture) {
        this.name = getFileName(defaultTexture);
        this.image = getImage(defaultTexture);
    }

    public ReadOnlyStringProperty nameProperty() {
        return new ReadOnlyStringWrapper(name);
    }

    public ReadOnlyObjectProperty<Image> imageProperty() {
        return new ReadOnlyObjectWrapper<>(image);
    }

    public IntegerProperty weightProperty() {
        return weight;
    }

    private Image getImage(Path path) {
        final Image imageFromPath;
        try (FileInputStream fileInputStream = new FileInputStream(path.toFile())) {
            imageFromPath = new Image(fileInputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return imageFromPath;
    }

    private String getFileName(Path path) {
        return path.getFileName().toString().replace(".png", "");
    }

    @Override
    public String toString() {
        return "VariantModel[" +
               "name='" + name + '\'' +
               ", image=" + image +
               ", weight=" + weight.get() +
               ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VariantModel that = (VariantModel) o;
        return name.equals(that.name) && image.equals(that.image) && weight.equals(that.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, image, weight);
    }

    @Override
    public int compareTo(VariantModel other) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.name, other.name);
    }
}
