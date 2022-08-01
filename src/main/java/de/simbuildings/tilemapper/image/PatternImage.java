package de.simbuildings.tilemapper.image;

import java.nio.file.Path;

/**
 * Represents an image that is intended to be used as a repeat pattern texture.
 */
public final class PatternImage extends AbstractImage {

    private PatternImage(Path path, String name) {
        super(path, name);
    }

    public static PatternImage of(Path imagePath) {
        return new PatternImage(imagePath, nameFromPath(imagePath));
    }

    @Override
    protected boolean isValid() {
        return resolution.isPowerOfTwo();
    }

    @Override
    public Image withName(String name) {
        return new PatternImage(file(), name);
    }
}
