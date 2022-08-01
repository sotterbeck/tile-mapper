package de.simbuildings.tilemapper.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

abstract class AbstractImage implements Image {
    protected final Path path;
    protected final ImageResolution resolution;
    protected final String name;

    AbstractImage(Path path, String name) {
        this.path = path;
        this.name = name;

        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(path.toFile());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        this.resolution = new ImageResolution(bufferedImage);
        if (!isValid()) {
            throw new IllegalArgumentException("image format is not valid");
        }
    }

    static String nameFromPath(Path imagePath) {
        String fileName = imagePath.getFileName().toString();
        return fileName.replace(".png", "");
    }

    abstract boolean isValid();

    @Override
    public ImageResolution resolution() {
        return resolution;
    }

    @Override
    public Path file() {
        return path;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractImage that)) return false;
        return Objects.equals(path, that.path) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, resolution, name);
    }

    @Override
    public String toString() {
        return "Image{" +
               "path=" + path +
               ", resolution=" + resolution +
               ", name='" + name + '\'' +
               '}';
    }
}
