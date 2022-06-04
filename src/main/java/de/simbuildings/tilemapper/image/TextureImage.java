package de.simbuildings.tilemapper.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class TextureImage implements Image {
    private final Path path;
    private final SquareImageResolution resolution;
    private final String name;

    private TextureImage(Path path, String name) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(path.toFile());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        this.path = path;
        this.name = name;
        this.resolution = new SquareImageResolution(bufferedImage.getHeight());
        if (!resolution.isPowerOfTwo()) {
            throw new IllegalArgumentException("image is not power of two");
        }
    }

    public static TextureImage of(Path imagePath) {
        return new TextureImage(imagePath,
                imagePath.getFileName().toString().replace(".png", ""));
    }

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
    public TextureImage withName(String name) {
        return new TextureImage(path, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextureImage that = (TextureImage) o;
        return Objects.equals(path, that.path) && Objects.equals(resolution, that.resolution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, resolution);
    }

    @Override
    public String toString() {
        return "TextureImage{" +
               "path=" + path +
               ", resolution=" + resolution +
               '}';
    }
}
