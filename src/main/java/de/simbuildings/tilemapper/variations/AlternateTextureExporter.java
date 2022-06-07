package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.image.TextureImage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableSet;

public class AlternateTextureExporter implements Exportable {
    private final List<Exportable> exporters;
    private final ObjectMapper objectMapper;
    private final Set<VariantDto> variants;
    private final String material;

    // TODO: builder for alternate texture configuration (rename function, namespace, etc.)
    public static AlternateTextureExporter create(ObjectMapper objectMapper, String material, Set<VariantDto> variants, BlockType block) {
        return new AlternateTextureExporter(objectMapper, material, variants, block);
    }

    private AlternateTextureExporter(ObjectMapper objectMapper, String material, Set<VariantDto> variants, BlockType blockType) {
        BiFunction<String, Integer, String> renameFunction = new NumberedRenameFunction();
        this.variants = variants;
        this.objectMapper = objectMapper;
        this.material = material;

        Exportable modelExporter = modelExporterOf(blockType, renameFunction);
        Exportable blockStateExporter = blockStateExporterOf(blockType, renameFunction);
        Exportable textureExporter = textureExporterOf(renameFunction);

        exporters = List.of(blockStateExporter, modelExporter, textureExporter);
    }

    private Exportable textureExporterOf(BiFunction<String, Integer, String> renameFunction) {
        Set<TextureImage> defaultTextures = new NamedTextureImageConverter(material, renameFunction)
                .fromDtos(variants);
        Set<TextureImage> additionalTextures = variants.stream()
                .flatMap(variant -> variant.additionalTextures().stream())
                .collect(toUnmodifiableSet());

        return new TextureExporter(material, Stream
                .concat(defaultTextures.stream(), additionalTextures.stream())
                .collect(toUnmodifiableSet()));
    }

    private Exportable modelExporterOf(BlockType blockType, BiFunction<String, Integer, String> renameFunction) {
        Set<ResourceVariant> resourceVariants = new ResourceVariantConverter(material, renameFunction)
                .fromDtos(variants);
        return new ModelJsonExporter(objectMapper, blockType, resourceVariants);
    }

    private Exportable blockStateExporterOf(BlockType blockType, BiFunction<String, Integer, String> renameFunction) {
        Set<BlockStateVariant.Builder> blockStateVariants = new BlockStateBuilderConverter(material, renameFunction)
                .fromDtos(variants);
        return new BlockStateJsonExporter(objectMapper, blockType, blockStateVariants);
    }

    @Override
    public void export(Path destination) throws IOException {
        for (Exportable exporter : exporters) {
            exporter.export(destination);
        }
    }

    @Override
    public Set<Path> conflictFiles(Path destination) {
        return exporters.stream()
                .flatMap(exporter -> exporter.conflictFiles(destination).stream())
                .collect(toUnmodifiableSet());
    }
}
