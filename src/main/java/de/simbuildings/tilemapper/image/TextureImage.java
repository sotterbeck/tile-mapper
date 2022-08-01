package de.simbuildings.tilemapper.image;

import java.nio.file.Path;

public final class TextureImage extends AbstractImage {

    private TextureImage(Path path, String name) {
        super(path, name);
    }

    public static TextureImage of(Path imagePath) {
        return new TextureImage(imagePath, imagePath.getFileName().toString().replace(".png", ""));
    }

    @Override
    boolean isValid() {
        return resolution.isPowerOfTwo() && resolution.isSquare();
    }

    @Override
    public TextureImage withName(String name) {
        return new TextureImage(file(), name);
    }
}
