package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.variations.model.Model;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableSet;

class ModelJsonExporterFactory implements JsonExporterFactory {
    private final ObjectMapper objectMapper;
    private final Set<Resource> resources;

    public ModelJsonExporterFactory(ObjectMapper objectMapper, Variant.Builder... variants) {
        this.resources = Arrays.stream(variants)
                .map(Variant.Builder::build)
                .map(Variant::resource)
                .collect(toUnmodifiableSet());
        this.objectMapper = objectMapper;
    }

    public static ModelJsonExporterFactory create(ObjectMapper objectMapper, Variant.Builder... variants) {
        return new ModelJsonExporterFactory(objectMapper, variants);
    }

    @Override
    public Exportable<Path> getExporter(BlockType... blockTypes) {
        Set<Model> models = new HashSet<>();
        for (BlockType blockType : blockTypes) {
            for (Resource resource : resources) {
                models.addAll(blockType.model(resource));
            }
        }
        return new BatchJsonExporter(objectMapper, models.stream()
                .collect(toMap(Function.identity(), Model::file))
        );
    }
}
