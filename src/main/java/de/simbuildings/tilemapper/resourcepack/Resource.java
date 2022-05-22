package de.simbuildings.tilemapper.resourcepack;

import de.simbuildings.tilemapper.variations.BlockType;
import de.simbuildings.tilemapper.variations.model.ModelFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
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
        createDirectories(path);
        return path;
    }

    public String modelLocation(ModelFile modelFile) {
        return "minecraft:block/" + material + "/" + modelFile.directory() + modelFile.fileName(variant);
    }

    public Path modelDirectory(ModelFile modelFile) {
        final Path path = Paths.get(ASSET_ROOT, NAMESPACE, "models", "block", material, modelFile.directory());
        createDirectories(path);
        return path;
    }

    public Path blockStateFile(BlockType blockType) {
        return Paths.get(ASSET_ROOT, NAMESPACE, "blockstates", material + blockType.blockStateSuffix() + ".json");
    }

    private void createDirectories(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
