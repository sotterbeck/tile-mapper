package de.simbuildings.tilemapper.common;

import de.simbuildings.tilemapper.variations.BlockType;

import java.nio.file.Path;

public interface ExportResource {
    Path outputFile(BlockType blockType);
}
