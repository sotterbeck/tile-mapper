package de.simbuildings.tilemapper.core.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.core.common.Exportable;
import de.simbuildings.tilemapper.core.variations.model.Model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableSet;

class ModelJsonExporter implements Exportable {
    private final ObjectMapper objectMapper;
    private final Set<VariantTextureInfo> textureInfos;
    private final Exportable exportable;

    ModelJsonExporter(ObjectMapper objectMapper, BlockType blockType, Collection<VariantTextureInfo> textureInfos) {
        this.textureInfos = textureInfos.stream()
                .collect(toUnmodifiableSet());
        this.objectMapper = objectMapper;
        this.exportable = getExporter(blockType);
    }

    public ModelJsonExporter(ObjectMapper objectMapper, BlockType blockType, VariantTextureInfo variantTextureInfo) {
        this(objectMapper, blockType, Set.of(variantTextureInfo));
    }

    private Exportable getExporter(BlockType blockType) {
        Set<Model> models = new HashSet<>();
        for (VariantTextureInfo variantTextureInfo : textureInfos) {
            models.addAll(blockType.createModels(variantTextureInfo.defaultResource(), variantTextureInfo));
        }
        return new BatchJsonExporter(objectMapper, models.stream()
                .collect(toMap(Function.identity(), Model::file)));
    }

    @Override
    public void export(Path destination) throws IOException {
        exportable.export(destination);
    }

    @Override
    public boolean hasConflict(Path destination) {
        return exportable.hasConflict(destination);
    }

    @Override
    public Set<Path> conflictFiles(Path destination) {
        return exportable.conflictFiles(destination);
    }
}
