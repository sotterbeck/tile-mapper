package de.simbuildings.tilemapper.core.image;

import java.nio.file.Path;

/**
 * An image with a resolution power of two in PNG format that is intended to be used in Minecraft resourcepacks.
 */
public final class TextureImage extends AbstractImage {

    private TextureImage(Path path, String name) {
        super(path, name);
    }

    /**
     * Returns a <code>TextureImage</code> instance.
     *
     * @param imagePath the path of the texture file.
     * @throws IllegalArgumentException If the image does not have a resolution power of two and is not in PNG format.
     */
    public static TextureImage of(Path imagePath) {
        return new TextureImage(imagePath, imagePath.getFileName().toString().replace(".png", ""));
    }

    @Override
    boolean isValid() {
        return resolution.isPowerOfTwo()
               && resolution.isSquare()
               && path.toString().toLowerCase().endsWith(".png");
    }

    @Override
    public TextureImage withName(String name) {
        return new TextureImage(file(), name);
    }
}
