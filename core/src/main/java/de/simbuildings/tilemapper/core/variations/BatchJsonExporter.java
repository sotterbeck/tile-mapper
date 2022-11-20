package de.simbuildings.tilemapper.core.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.core.common.Exportable;
import de.simbuildings.tilemapper.core.util.PathUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

class BatchJsonExporter implements Exportable {
    private final ObjectMapper objectMapper;
    private final Map<Object, Path> exportResourceMap;

    BatchJsonExporter(ObjectMapper objectMapper, Map<Object, Path> exportResources) {
        this.objectMapper = objectMapper;
        this.exportResourceMap = exportResources;
    }

    @Override
    public void export(Path destination) throws IOException {
        for (Map.Entry<Object, Path> exportResourceEntry : exportResourceMap.entrySet()) {
            final Path outputPath = destination.resolve(exportResourceEntry.getValue());
            PathUtils.createDirectories(outputPath.getParent());
            objectMapper.writeValue(outputPath.toFile(), exportResourceEntry.getKey());
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
                .map(destination::resolve)
                .filter(Files::exists)
                .collect(toUnmodifiableSet());
    }
}