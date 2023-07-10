package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;

import java.util.Set;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * Creates a <Code>BlockState</Code> and <code>Models</code> for a slab.
 */
public final class SlabJsonFactory implements ResourcePackJsonFactory {
    @Override
    public BlockState blockState(Set<BlockStateVariantBuilder> variants) {
        return new BlockStateProvider(variants).get();
    }

    @Override
    public Set<Model> models(Resource model, Resource bottom, Resource top, Resource side) {
        VariantTextureInfo variantTextureInfo = new VariantTextureInfo.Builder(model)
                .addTexture("slab", "bottom", bottom)
                .addTexture("slab", "top", top)
                .addTexture("slab", "side", side)
                .build();
        return models(model, variantTextureInfo);
    }

    @Override
    public Set<Model> models(Resource modelResource, VariantTextureInfo textures) {
        Resource bottom = textures.getTexture("slab", "bottom");
        Resource top = textures.getTexture("slab", "top");
        Resource side = textures.getTexture("slab", "side");
        TriFaceModel modelFactory = new TriFaceModel(modelResource, bottom, top, side);
        return Set.of(
                modelFactory.createModel(ModelFile.SLAB),
                modelFactory.createModel(ModelFile.SLAB_TOP)
        );
    }

    private static class BlockStateProvider implements Supplier<BlockState> {
        private final Set<BlockStateVariantBuilder> bottomVariants;
        private final Set<BlockStateVariantBuilder> doubleVariants;
        private final Set<BlockStateVariantBuilder> topVariants;

        public BlockStateProvider(Set<BlockStateVariantBuilder> variants) {
            this.bottomVariants = variants;
            this.doubleVariants = variants;
            this.topVariants = variants;
        }

        public BlockState get() {
            return new JacksonBlockState.Builder()
                    .fileSuffix("slab")
                    .variants("type=bottom", bottomVariants.stream()
                            .map(builder -> builder.modelType(ModelFile.SLAB))
                            .map(BlockStateVariantBuilder::build)
                            .collect(toUnmodifiableSet()))
                    .variants("type=double", doubleVariants.stream()
                            .map(builder -> builder.modelType(ModelFile.BLOCK))
                            .map(BlockStateVariantBuilder::build)
                            .collect(toUnmodifiableSet()))
                    .variants("type=top", topVariants.stream()
                            .map(builder -> builder.modelType(ModelFile.SLAB_TOP))
                            .map(BlockStateVariantBuilder::build)
                            .collect(toUnmodifiableSet()))
                    .build();
        }
    }
}
