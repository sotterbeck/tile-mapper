package de.simbuildings.tilemapper.resourcepack;

import de.simbuildings.tilemapper.variations.model.ModelFile;

import java.nio.file.Path;

public record Resource(String material, String variant) {

    public Resource(String material) {
        this(material, material);
    }

    public String textureLocation() {
        return "minecraft:block/%s/%s".formatted(material, variant);
    }

    public String modelLocation(ModelFile modelFile) {
        return "minecraft:block/" + material + "/" + modelFile.directory() + modelFile.fileName(variant);
    }

    public Path modelPath(Resourcepack resourcepack, ModelFile modelFile) {
        return resourcepack.modelDirectory(material);
    }
}
