package de.simbuildings.tilemapper.image;

import java.nio.file.Path;

public interface Image {
    ImageResolution resolution();

    Path file();

    String name();
}
