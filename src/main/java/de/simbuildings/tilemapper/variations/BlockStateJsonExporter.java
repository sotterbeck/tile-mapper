package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.variations.blockstate.BlockState;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static java.util.stream.Collectors.toUnmodifiableSet;

class BlockStateJsonExporter implements Exportable {
    private final ObjectMapper objectMapper;
    private final BlockType blockType;
    private final Set<BlockStateVariant.Builder> variants;
    private final Exportable exporter;

    public BlockStateJsonExporter(ObjectMapper objectMapper, BlockType blockType, BlockStateVariant.Builder... variant) {
        this.blockType = blockType;
        this.objectMapper = objectMapper;
        this.variants = Arrays.stream(variant)
                .collect(toUnmodifiableSet());
        this.exporter = getExporter();
    }

    public BlockStateJsonExporter(ObjectMapper objectMapper, BlockType blockType, Collection<BlockStateVariant.Builder> variants) {
        this(objectMapper, blockType, variants.toArray(BlockStateVariant.Builder[]::new));
    }

    private Exportable getExporter() {
        Map<Object, Path> exportResources = new HashMap<>();
        BlockState blockState = blockType.createBlockState(variants);
        String material = getMaterial();

        exportResources.put(blockState, blockState.resourcepackLocation(material));
        return new BatchJsonExporter(objectMapper, exportResources);
    }

    private String getMaterial() {
        return variants.stream()
                .map(BlockStateVariant.Builder::build)
                .map(BlockStateVariant::resource)
                .map(Resource::material)
                .findFirst()
                .orElseThrow();
    }

    @Override
    public void export(Path destination) throws IOException {
        exporter.export(destination);
    }

    @Override
    public boolean hasConflict(Path destination) {
        return exporter.hasConflict(destination);
    }

    @Override
    public Set<Path> conflictFiles(Path destination) {
        return exporter.conflictFiles(destination);
    }
}
