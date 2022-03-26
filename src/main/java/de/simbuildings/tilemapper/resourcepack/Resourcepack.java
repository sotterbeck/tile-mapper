package de.simbuildings.tilemapper.resourcepack;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.nio.file.Files;
import java.nio.file.Path;

@JsonSerialize
public record Resourcepack(String name, Path directory) {
    public Resourcepack(Path directory) {
        this(directory.getFileName().toString(), directory);
    }

    public boolean isResourcepackDirectory() {
        return Files.exists(directory.resolve("pack.mcmeta"));
    }
}
