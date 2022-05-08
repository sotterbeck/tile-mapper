package de.simbuildings.tilemapper.resourcepack;

import de.simbuildings.tilemapper.variations.ModelType;

import java.nio.file.Path;

public record Resource(Resourcepack resourcepack, String material, String variant) {

    public Resource(Resourcepack resourcepack, String blockName) {
        this(resourcepack, blockName, blockName);
    }

    public String textureLocation() {
        return "minecraft:block/%s/%s".formatted(material, variant);
    }

    public String modelLocation(ModelType modelType) {
        return "minecraft:block/" + material + "/" + modelType.directory() + modelType.fileName(variant);
    }

    public Path texturePath() {
        return resourcepack.textureDirectory(material);
    }

    public Path modelPath() {
        return resourcepack.modelDirectory(material);
    }
}
