package de.simbuildings.tilemapper.tile;

import de.simbuildings.tilemapper.common.Exportable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ImageSplitterExporter implements Exportable {
    private final ImageSplitter imageSplitter;

    ImageSplitterExporter(ImageSplitter imageSplitter) {
        this.imageSplitter = imageSplitter;
    }

    @Override
    public void export(Path destinationDirectory) throws IOException {
        for (Tile tile : imageSplitter.getTiles()) {
            tile.export(destinationDirectory);
        }
    }

    @Override
    public boolean hasConflict(Path destinationDirectory) {
        return imageSplitter.getTiles().stream()
                .anyMatch(tile -> tile.hasConflict(destinationDirectory));
    }

    @Override
    public Set<Path> conflictFiles(Path destinationDirectory) {
        try (Stream<Path> pathStream = Files.list(destinationDirectory)) {
            Set<Path> outputPaths = getOutputPaths(destinationDirectory);
            return pathStream
                    .filter(outputPaths::contains)
                    .collect(Collectors.toUnmodifiableSet());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Set<Path> getOutputPaths(Path destinationDirectory) {
        return imageSplitter.getTiles().stream()
                .map(tile -> tile.getOutputPath(destinationDirectory))
                .collect(Collectors.toUnmodifiableSet());
    }
}