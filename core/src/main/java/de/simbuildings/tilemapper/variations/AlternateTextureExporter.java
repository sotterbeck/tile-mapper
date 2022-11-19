package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.image.TextureImage;
import de.simbuildings.tilemapper.resourcepack.Resource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableSet;

public class AlternateTextureExporter implements Exportable {
    private final ObjectMapper objectMapper;

    private final List<Exportable> exporters;
    private final Set<Variant> variants;

    private final String material;
    private final String namespace;

    private final TextureImage defaultTexture;
    private final BiFunction<String, Integer, String> renameFunction;

    public static Builder builder(ObjectMapper objectMapper, String material, Collection<Variant> variants) {
        return new Builder(objectMapper, material, variants);
    }

    private AlternateTextureExporter(Builder builder) {
        this.objectMapper = builder.objectMapper;
        this.variants = builder.variants;
        this.material = builder.material;
        this.namespace = builder.namespace;
        this.renameFunction = builder.renameFunction;
        this.defaultTexture = builder.defaultTexture;
        this.exporters = exportables(builder.blockTypes);
    }

    private List<Exportable> exportables(Collection<BlockType> blockTypes) {
        List<Exportable> exportables = new ArrayList<>();
        for (BlockType blockType : blockTypes) {
            exportables.add(modelExporterOf(blockType));
            exportables.add(blockStateExporterOf(blockType));
        }
        exportables.add(textureExporter());
        return Collections.unmodifiableList(exportables);
    }

    private Exportable textureExporter() {
        Set<TextureImage> defaultTextures = new NamedTextureImageConverter(material, renameFunction)
                .fromDtos(variants);
        Set<TextureImage> additionalTextures = variants.stream()
                .flatMap(variant -> variant.additionalTextures().stream())
                .collect(toUnmodifiableSet());
        Set<TextureImage> textures = Stream.concat(defaultTextures.stream(), additionalTextures.stream())
                .collect(toUnmodifiableSet());

        return new TextureExporter(material, textures, defaultTexture, namespace);
    }

    private Exportable modelExporterOf(BlockType blockType) {
        Set<ResourceVariant> resourceVariants = new ResourceVariantConverter(material, renameFunction, namespace)
                .fromDtos(variants);
        return new ModelJsonExporter(objectMapper, blockType, resourceVariants);
    }

    private Exportable blockStateExporterOf(BlockType blockType) {
        Set<BlockStateVariant.Builder> blockStateVariants = new BlockStateBuilderConverter(material, renameFunction, namespace)
                .fromDtos(variants);
        return new BlockStateJsonExporter(objectMapper, namespace, blockType, blockStateVariants);
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

    public static class Builder {
        private final Set<Variant> variants;
        private final String material;
        private final ObjectMapper objectMapper;

        private Set<BlockType> blockTypes;
        private BiFunction<String, Integer, String> renameFunction = new NumberedRenameFunction();
        private TextureImage defaultTexture;
        private String namespace = Resource.DEFAULT_NAMESPACE;

        Builder(ObjectMapper objectMapper, String material, Collection<Variant> variants) {
            this.material = material;
            this.variants = new HashSet<>(variants);
            this.objectMapper = objectMapper;
            this.blockTypes = Set.of(BlockType.BLOCK);
        }

        public Builder renameFunction(BiFunction<String, Integer, String> renameFunction) {
            this.renameFunction = renameFunction;
            return this;
        }

        public Builder blockTypes(Collection<BlockType> blockTypes) {
            this.blockTypes = new HashSet<>(blockTypes);
            return this;
        }

        public Builder blockTypes(BlockType... blockTypes) {
            this.blockTypes = Arrays.stream(blockTypes)
                    .collect(toUnmodifiableSet());
            return this;
        }

        public Builder namespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder defaultTexture(TextureImage defaultTexture) {
            this.defaultTexture = defaultTexture;
            return this;
        }

        public Exportable build() {
            return new AlternateTextureExporter(this);
        }
    }
}
