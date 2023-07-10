package de.simbuildings.tilemapper.core.variations;

import java.nio.file.Path;
import java.util.Map;

public interface Model {
    Path file();

    String parent();

    Map<String, String> textures();
}
