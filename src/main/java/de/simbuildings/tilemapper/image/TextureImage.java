package de.simbuildings.tilemapper.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class TextureImage implements Image {
    private final Path path;
    private final BufferedImage bufferedImage;
    private final SquareImageResolution resolution;

    private TextureImage(Path path) {
        try {
            bufferedImage = ImageIO.read(path.toFile());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        this.path = path;
        resolution = new SquareImageResolution(bufferedImage.getHeight());
        if (!resolution.isPowerOfTwo()) {
            throw new IllegalArgumentException("image is not power of two");
        }
    }

    public static TextureImage of(Path imagePath) {
        return new TextureImage(imagePath);
    }

    @Override
    public ImageResolution resolution() {
        return resolution;
    }

    @Override
    public Path file() {
        return path;
    }
}
