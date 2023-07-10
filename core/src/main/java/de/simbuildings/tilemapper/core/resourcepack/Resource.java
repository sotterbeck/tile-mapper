package de.simbuildings.tilemapper.core.resourcepack;

import de.simbuildings.tilemapper.core.variations.ModelFile;

import java.nio.file.Path;
import java.nio.file.Paths;

public record Resource(String material, String variant, String namespace) {

    public static final String DEFAULT_NAMESPACE = "minecraft";
    private static final String ASSET_ROOT = "assets";

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
        return Paths.get(ASSET_ROOT, namespace, "textures", "block", material);
    }

    public String modelLocation(ModelFile modelFile) {
        return namespace + ":block/" + material + "/" + modelFile.parentDirectory() + modelFile.fileName(variant);
    }

    public Path modelFile(ModelFile modelFile) {
        return Paths.get(ASSET_ROOT,
                namespace,
                "models",
                "block",
                material,
                modelFile.parentDirectory(),
                modelFile.fileName(variant) + ".json");
    }

    public Resource withNamespace(String namespace) {
        return new Resource(material, variant, namespace);
    }
}
