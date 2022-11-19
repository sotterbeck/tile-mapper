package de.simbuildings.tilemapper.resourcepack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

@JsonSerialize
public record Resourcepack(String name, Path directory) implements Comparable<Resourcepack> {
    public Resourcepack(Path directory) {
        this(directory.getFileName().toString(), directory);
    }

    private static final Comparator<Resourcepack> COMPARATOR = Comparator
            .comparing(Resourcepack::name)
            .thenComparing(Resourcepack::directory);

    @JsonIgnore
    public boolean isResourcepackDirectory() {
        return Files.exists(directory.resolve("pack.mcmeta"));
    }

    @Override
    public int compareTo(Resourcepack resourcepack) {
        return COMPARATOR.compare(this, resourcepack);
    }
}
