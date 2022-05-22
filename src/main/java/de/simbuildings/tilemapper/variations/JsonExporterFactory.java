package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.common.Exportable;

import java.nio.file.Path;

public interface JsonExporterFactory {
    Exportable<Path> getExporter(BlockType... blockTypes);
}
