package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.variations.blockstate.BlockState;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

class BlockStateJsonExporterFactory implements JsonExporterFactory {
    private final ObjectMapper objectMapper;
    private final Set<Variant.Builder> variants;

    private BlockStateJsonExporterFactory(ObjectMapper objectMapper, Variant.Builder... variants) {
        this.variants = Arrays.stream(variants)
                .collect(toUnmodifiableSet());
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
            BlockState blockState = blockType.createBlockState(variants);
            exportResources.put(blockState, blockState.resourcepackLocation(material));
        }
        return new BatchJsonExporter(objectMapper, exportResources);
    }
}
