package de.simbuildings.tilemapper.junit;

import de.simbuildings.tilemapper.image.TextureImage;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils {

    private TestUtils() {
    }

    public static String fileNameOf(Path file) {
        return file.getFileName().toString();
    }

    public static TextureImage getSampleTexture(String name) {
        return TextureImage.of(Paths.get("src", "test", "resources", "image", name));
    }
}
