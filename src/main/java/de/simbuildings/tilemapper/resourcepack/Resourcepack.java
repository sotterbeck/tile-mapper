package de.simbuildings.tilemapper.resourcepack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

@JsonSerialize
public record Resourcepack(String name, Path directory) implements Comparable<Resourcepack> {
    public Resourcepack(Path directory) {
        this(directory.getFileName().toString(), directory);
    }

    private static final Path MINECRAFT_ASSET_PATH = Paths.get("assets", "minecraft");

    private static final Comparator<Resourcepack> COMPARATOR = Comparator
            .comparing(Resourcepack::name)
            .thenComparing(Resourcepack::directory);

    @JsonIgnore
    public boolean isResourcepackDirectory() {
        return Files.exists(directory.resolve("pack.mcmeta"));
    }

    @JsonIgnore
    public Path modelDirectory(String blockName) {
        Path modelPath = assetPathForBlock(blockName, "models");
        createDirectories(modelPath);
        return modelPath;
    }

    @JsonIgnore
    public Path textureDirectory(String blockName) {
        Path texturePath = assetPathForBlock(blockName, "textures");
        createDirectories(texturePath);
        return texturePath;
    }

    @JsonIgnore
    public Path blockstatesDirectory() {
        Path blockstatesPath = directory.resolve(MINECRAFT_ASSET_PATH).resolve("blockstates");
        createDirectories(blockstatesPath);
        return blockstatesPath;
    }

    private Path assetPathForBlock(String blockName, String folder) {
        return directory.resolve(MINECRAFT_ASSET_PATH).resolve(
                Paths.get(folder, "block", folderNameForBlock(blockName))
        );
    }

    private String folderNameForBlock(String blockName) {
        return blockName.replace("minecraft:", "");
    }

    private void createDirectories(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }


    @Override
    public int compareTo(Resourcepack resourcepack) {
        return COMPARATOR.compare(this, resourcepack);
    }
}
