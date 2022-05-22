package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.common.Exportable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

class BatchJsonExporter implements Exportable<Path> {
    private final ObjectMapper objectMapper;
    private final Map<Object, Path> exportResourceMap;

    private static void createParentDirectoryForFile(Path file) {
        Objects.requireNonNull(file.getParent());
        try {
            Files.createDirectories(file.getParent());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    BatchJsonExporter(ObjectMapper objectMapper, Map<Object, Path> exportResources) {
        this.objectMapper = objectMapper;
        this.exportResourceMap = exportResources;
    }

    @Override
    public void export(Path destination) throws IOException {
        for (Map.Entry<Object, Path> exportResourceEntry : exportResourceMap.entrySet()) {
            final Path outputPath = destination.resolve(exportResourceEntry.getValue());
            createParentDirectoryForFile(outputPath);
            objectMapper.writeValue(outputPath.toFile(), exportResourceEntry);
        }
    }

    @Override
    public boolean hasConflict(Path destination) {
        return exportResourceMap.values().stream()
                .anyMatch(Files::exists);
    }

    @Override
    public Set<Path> conflictFiles(Path destination) {
        return exportResourceMap.values().stream()
                .filter(Files::exists)
                .collect(Collectors.toSet());
    }
}