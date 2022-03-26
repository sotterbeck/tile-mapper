package de.simbuildings.tilemapper.resourcepack;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.nio.file.Path;

@JsonSerialize
public record Resourcepack(String name, Path directory) {
}
