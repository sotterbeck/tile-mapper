package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.common.ExportResource;
import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.resourcepack.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

class BlockStateJsonExporterFactory implements JsonExporterFactory {
    private final ObjectMapper objectMapper;
    private final Map<Variant.Builder, Resource> variantMap;

    private BlockStateJsonExporterFactory(ObjectMapper objectMapper, Variant.Builder... variants) {
        this.variantMap = Arrays.stream(variants)
                .collect(toMap(Function.identity(), builder -> builder.build().resource()));
        this.objectMapper = objectMapper;
    }

    public static BlockStateJsonExporterFactory create(ObjectMapper objectMapper, Variant.Builder... variants) {
        return new BlockStateJsonExporterFactory(objectMapper, variants);
    }

    @Override
    public Exportable<Path> getExporter(BlockType... blockTypes) {
        final String material = variantMap.values().stream()
                .map(Resource::material)
                .findFirst()
                .orElseThrow();

        Map<ExportResource, Path> exportResources = new HashMap<>();
        for (BlockType blockType : blockTypes) {
            exportResources.put(blockType.blockState(variantMap.keySet()), new Resource(material).blockStateFile(blockType));

        }
        return new BatchJsonExporter(objectMapper, exportResources);
    }

    private static class BatchJsonExporter implements Exportable<Path> {
        private final ObjectMapper objectMapper;
        private final Map<ExportResource, Path> exportResourceMap;

        private static void createParentDirectoryForFile(Path file) {
            Objects.requireNonNull(file.getParent());
            try {
                Files.createDirectories(file.getParent());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        private BatchJsonExporter(ObjectMapper objectMapper, Map<ExportResource, Path> exportResources) {
            this.objectMapper = objectMapper;
            this.exportResourceMap = exportResources;
        }

        @Override
        public void export(Path destination) throws IOException {
            for (Map.Entry<ExportResource, Path> exportResourceEntry : exportResourceMap.entrySet()) {
                final Path outputPath = destination.resolve(exportResourceEntry.getValue());
                createParentDirectoryForFile(outputPath);
                objectMapper.writeValue(outputPath.toFile(), exportResourceEntry);
            }
        }

        @Override
        public boolean hasConflict(Path destination) {
            return false;
        }

        @Override
        public Set<Path> conflictFiles(Path destination) {
            return Collections.emptySet();
        }
    }
}
