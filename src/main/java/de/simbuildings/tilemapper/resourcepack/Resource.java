package de.simbuildings.tilemapper.resourcepack;

import de.simbuildings.tilemapper.util.PathUtils;
import de.simbuildings.tilemapper.variations.model.ModelFile;

import java.nio.file.Path;
import java.nio.file.Paths;

public record Resource(String material, String variant) {

    public static final String ASSET_ROOT = "assets";
    public static final String NAMESPACE = "minecraft";

    public Resource(String material) {
        this(material, material);
    }

    public String textureLocation() {
        return "minecraft:block/%s/%s".formatted(material, variant);
    }

    public Path textureDirectory() {
        final Path path = Paths.get(ASSET_ROOT, NAMESPACE, "textures", "block", material);
        PathUtils.createDirectories(path);
        return path;
    }

    public String modelLocation(ModelFile modelFile) {
        return "minecraft:block/" + material + "/" + modelFile.parentDirectory() + modelFile.fileName(variant);
    }

    public Path modelFile(ModelFile modelFile) {
        final Path path = Paths.get(ASSET_ROOT, NAMESPACE, "models", "block", material, modelFile.parentDirectory(), modelFile.fileName(variant) + ".json");
        PathUtils.createDirectories(path);
        return path;
    }
}
