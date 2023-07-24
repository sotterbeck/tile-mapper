package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Creates a <Code>BlockState</Code> and <code>Models</code> for stairs.
 */
public final class StairsJsonFactory implements ResourcePackJsonFactory {
    @Override
    public BlockState blockState(Set<BlockStateVariantBuilder> variants) {
        return new BlockStateProvider(variants).get();
    }

    @Override
    public Set<Model> models(Resource modelResource, VariantTextureInfo textures) {
        Resource bottom = textures.getTexture("stairs", "bottom");
        Resource top = textures.getTexture("stairs", "top");
        Resource side = textures.getTexture("stairs", "side");
        TriFaceModel modelFactory = new TriFaceModel(modelResource, bottom, top, side);
        return Set.of(
                modelFactory.createModel(ModelFile.STAIRS),
                modelFactory.createModel(ModelFile.STAIRS_INNER),
                modelFactory.createModel(ModelFile.STAIRS_OUTER)
        );
    }

    private static class BlockStateProvider implements Supplier<BlockState> {
        private final Set<BlockStateVariantBuilder> variants;
        private final JacksonBlockState.Builder blockStateBuilder = new JacksonBlockState.Builder();

        private BlockStateProvider(Set<BlockStateVariantBuilder> variants) {
            this.variants = variants;
        }

        @Override
        public BlockState get() {
            buildEast();
            buildNorth();
            buildSouth();
            buildWest();
            return blockStateBuilder.build();
        }

        private Stream<BlockStateVariantBuilder> baseVariantStream(ModelFile type) {
            return variants.stream()
                    .map(BlockStateVariantBuilder::new)
                    .map(builder -> builder
                            .modelType(type)
                            .uvLock(true));
        }

        private void buildEast() {
            blockStateBuilder.variantStream("facing=east,half=bottom,shape=inner_left", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder.rotationY(270)))
                    .variantStream("facing=east,half=bottom,shape=inner_right", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder.uvLock(false)))
                    .variantStream("facing=east,half=bottom,shape=outer_left", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder.rotationY(270)))
                    .variantStream("facing=east,half=bottom,shape=outer_right", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder.uvLock(false)))
                    .variantStream("facing=east,half=bottom,shape=straight", baseVariantStream(ModelFile.STAIRS)
                            .map(builder -> builder.uvLock(false)))
                    .variantStream("facing=east,half=top,shape=inner_left", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder.rotationX(180)))
                    .variantStream("facing=east,half=top,shape=inner_right", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(90)))
                    .variantStream("facing=east,half=top,shape=outer_left", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder.rotationX(180)))
                    .variantStream("facing=east,half=top,shape=outer_right", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(90)))
                    .variantStream("facing=east,half=top,shape=straight", baseVariantStream(ModelFile.STAIRS)
                            .map(builder -> builder.rotationX(180)));
        }

        private void buildNorth() {
            blockStateBuilder.variantStream("facing=north,half=bottom,shape=inner_left", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder.rotationY(180)))
                    .variantStream("facing=north,half=bottom,shape=inner_right", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder.rotationY(270)))
                    .variantStream("facing=north,half=bottom,shape=outer_left", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder.rotationY(180)))
                    .variantStream("facing=north,half=bottom,shape=outer_right", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder.rotationY(270)))
                    .variantStream("facing=north,half=bottom,shape=straight", baseVariantStream(ModelFile.STAIRS)
                            .map(builder -> builder.rotationY(270)))
                    .variantStream("facing=north,half=top,shape=inner_left", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(270)))
                    .variantStream("facing=north,half=top,shape=inner_right", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder.rotationX(180)))
                    .variantStream("facing=north,half=top,shape=outer_left", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(270)))
                    .variantStream("facing=north,half=top,shape=outer_right", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder.rotationX(180)))
                    .variantStream("facing=north,half=top,shape=straight", baseVariantStream(ModelFile.STAIRS)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(270)));
        }

        private void buildSouth() {
            blockStateBuilder.variantStream("facing=south,half=bottom,shape=inner_left", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder.uvLock(false)))
                    .variantStream("facing=south,half=bottom,shape=inner_right", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder.rotationY(90)))
                    .variantStream("facing=south,half=bottom,shape=outer_left", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder.uvLock(false)))
                    .variantStream("facing=south,half=bottom,shape=outer_right", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder.rotationY(90)))
                    .variantStream("facing=south,half=bottom,shape=straight", baseVariantStream(ModelFile.STAIRS)
                            .map(builder -> builder.rotationY(90)))
                    .variantStream("facing=south,half=top,shape=inner_left", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(90)))
                    .variantStream("facing=south,half=top,shape=inner_right", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(180)))
                    .variantStream("facing=south,half=top,shape=outer_left", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(90)))
                    .variantStream("facing=south,half=top,shape=outer_right", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(180)))
                    .variantStream("facing=south,half=top,shape=straight", baseVariantStream(ModelFile.STAIRS)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(90)));
        }


        private void buildWest() {
            blockStateBuilder.variantStream("facing=west,half=bottom,shape=inner_left", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder.rotationY(90)))
                    .variantStream("facing=west,half=bottom,shape=inner_right", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder.rotationY(180)))
                    .variantStream("facing=west,half=bottom,shape=outer_left", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder.rotationY(90)))
                    .variantStream("facing=west,half=bottom,shape=outer_right", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder.rotationY(180)))
                    .variantStream("facing=west,half=bottom,shape=straight", baseVariantStream(ModelFile.STAIRS)
                            .map(builder -> builder.rotationY(180)))
                    .variantStream("facing=west,half=top,shape=inner_left", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(180)))
                    .variantStream("facing=west,half=top,shape=inner_right", baseVariantStream(ModelFile.STAIRS_INNER)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(270)))
                    .variantStream("facing=west,half=top,shape=outer_left", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(180)))
                    .variantStream("facing=west,half=top,shape=outer_right", baseVariantStream(ModelFile.STAIRS_OUTER)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(270)))
                    .variantStream("facing=west,half=top,shape=straight", baseVariantStream(ModelFile.STAIRS)
                            .map(builder -> builder
                                    .rotationX(180)
                                    .rotationY(180)));
        }
    }
}
