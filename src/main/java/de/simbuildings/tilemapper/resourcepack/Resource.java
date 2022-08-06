package de.simbuildings.tilemapper.resourcepack;

import de.simbuildings.tilemapper.util.PathUtils;
import de.simbuildings.tilemapper.variations.model.ModelFile;

import java.nio.file.Path;
import java.nio.file.Paths;

public record Resource(String material, String variant, String namespace) {

    public static final String ASSET_ROOT = "assets";
    public static final String DEFAULT_NAMESPACE = "minecraft";

    public Resource(String material, String variant) {
        this(material, variant, DEFAULT_NAMESPACE);
    }

    public Resource(String material) {
        this(material, material, DEFAULT_NAMESPACE);
    }

    public Path blockStateDirectory(String fileSuffix) {
        return Paths.get(ASSET_ROOT, namespace, "blockstates", material + fileSuffix + ".json");
    }

    public String textureLocation() {
        return (namespace + ":block/%s/%s").formatted(material, variant);
    }

    public Path textureDirectory() {
        final Path path = Paths.get(ASSET_ROOT, namespace, "textures", "block", material);
        PathUtils.createDirectories(path);
        return path;
    }

    public String modelLocation(ModelFile modelFile) {
        return namespace + ":block/" + material + "/" + modelFile.parentDirectory() + modelFile.fileName(variant);
    }

    public Path modelFile(ModelFile modelFile) {
        final Path path = Paths.get(ASSET_ROOT, namespace, "models", "block", material, modelFile.parentDirectory(), modelFile.fileName(variant) + ".json");
        PathUtils.createDirectories(path);
        return path;
    }

    public Resource withNamespace(String namespace) {
        return new Resource(material, variant, namespace);
    }
}
