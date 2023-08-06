package de.simbuildings.tilemapper.core.resourcepack;

import de.simbuildings.tilemapper.core.variations.ModelFile;

import java.nio.file.Path;

public interface ResourcepackPathProvider {
    String textureLocation();

    Path textureDirectory();

    String modelLocation(ModelFile modelFile);

    Path modelPath(ModelFile modelFile);
}
