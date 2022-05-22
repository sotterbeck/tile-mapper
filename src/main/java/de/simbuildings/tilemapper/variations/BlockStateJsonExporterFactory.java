package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.resourcepack.Resource;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class BlockStateJsonExporterFactory implements JsonExporterFactory {
    private final ObjectMapper objectMapper;
    private final Set<Variant.Builder> variants;

    private BlockStateJsonExporterFactory(ObjectMapper objectMapper, Variant.Builder... variants) {
        this.variants = Arrays.stream(variants)
                .collect(Collectors.toUnmodifiableSet());
        this.objectMapper = objectMapper;
    }

    public static BlockStateJsonExporterFactory create(ObjectMapper objectMapper, Variant.Builder... variants) {
        return new BlockStateJsonExporterFactory(objectMapper, variants);
    }

    @Override
    public Exportable<Path> getExporter(BlockType... blockTypes) {
        final String material = variants.stream()
                .map(Variant.Builder::build)
                .map(Variant::resource)
                .map(Resource::material)
                .findFirst()
                .orElseThrow();

        Map<Object, Path> exportResources = new HashMap<>();
        for (BlockType blockType : blockTypes) {
            exportResources.put(blockType.blockState(variants), new Resource(material).blockStateFile(blockType));
        }
        return new BatchJsonExporter(objectMapper, exportResources);
    }
}
