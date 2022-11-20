package de.simbuildings.tilemapper.core.image;

import java.nio.file.Path;

public interface Image {
    ImageResolution resolution();

    Path file();

    String name();

    Image withName(String name);
}
