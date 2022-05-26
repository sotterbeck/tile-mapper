package de.simbuildings.tilemapper.variations.blockstate;

import de.simbuildings.tilemapper.variations.Variant;
import de.simbuildings.tilemapper.variations.model.ModelFile;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static de.simbuildings.tilemapper.variations.model.ModelFile.*;

class StairsBlockStateFactory implements Supplier<BlockState> {

    private final Set<Variant.Builder> variants;
    private final BlockState.Builder blockStateBuilder = new BlockState.Builder();

    public StairsBlockStateFactory(Set<Variant.Builder> variants) {
        this.variants = variants;
    }

    @Override
    public BlockState get() {
        buildEast();
        buildNorth();
        buildSouth();
        buildWest();
        return blockStateBuilder
                .fileSuffix("stairs")
                .build();
    }

    private void buildEast() {
        blockStateBuilder.variantStream("facing=east,half=bottom,shape=inner_left", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder.rotationY(270)))
                .variantStream("facing=east,half=bottom,shape=inner_right", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder.uvLock(false)))
                .variantStream("facing=east,half=bottom,shape=outer_left", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder.rotationY(270)))
                .variantStream("facing=east,half=bottom,shape=outer_right", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder.uvLock(false)))
                .variantStream("facing=east,half=bottom,shape=straight", baseVariantStream(STAIRS)
                        .map(builder -> builder.uvLock(false)))
                .variantStream("facing=east,half=top,shape=inner_left", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder.rotationX(180)))
                .variantStream("facing=east,half=top,shape=inner_right", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(90)))
                .variantStream("facing=east,half=top,shape=outer_left", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder.rotationX(180)))
                .variantStream("facing=east,half=top,shape=outer_right", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(90)))
                .variantStream("facing=east,half=top,shape=straight", baseVariantStream(STAIRS)
                        .map(builder -> builder.rotationX(180)));
    }

    private void buildNorth() {
        blockStateBuilder.variantStream("facing=north,half=bottom,shape=inner_left", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder.rotationY(180)))
                .variantStream("facing=north,half=bottom,shape=inner_right", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder.rotationY(270)))
                .variantStream("facing=north,half=bottom,shape=outer_left", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder.rotationY(180)))
                .variantStream("facing=north,half=bottom,shape=outer_right", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder.rotationY(270)))
                .variantStream("facing=north,half=bottom,shape=straight", baseVariantStream(STAIRS)
                        .map(builder -> builder.rotationY(270)))
                .variantStream("facing=north,half=top,shape=inner_left", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(270)))
                .variantStream("facing=north,half=top,shape=inner_right", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder.rotationX(180)))
                .variantStream("facing=north,half=top,shape=outer_left", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(270)))
                .variantStream("facing=north,half=top,shape=outer_right", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder.rotationX(180)))
                .variantStream("facing=north,half=top,shape=straight", baseVariantStream(STAIRS)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(270)));
    }

    private void buildSouth() {
        blockStateBuilder.variantStream("facing=south,half=bottom,shape=inner_left", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder.uvLock(false)))
                .variantStream("facing=south,half=bottom,shape=inner_right", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder.rotationY(90)))
                .variantStream("facing=south,half=bottom,shape=outer_left", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder.uvLock(false)))
                .variantStream("facing=south,half=bottom,shape=outer_right", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder.rotationY(90)))
                .variantStream("facing=south,half=bottom,shape=straight", baseVariantStream(STAIRS)
                        .map(builder -> builder.rotationY(90)))
                .variantStream("facing=south,half=top,shape=inner_left", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(90)))
                .variantStream("facing=south,half=top,shape=inner_right", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(180)))
                .variantStream("facing=south,half=top,shape=outer_left", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(90)))
                .variantStream("facing=south,half=top,shape=outer_right", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(180)))
                .variantStream("facing=south,half=top,shape=straight", baseVariantStream(STAIRS)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(90)));
    }


    private void buildWest() {
        blockStateBuilder.variantStream("facing=west,half=bottom,shape=inner_left", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder.rotationY(90)))
                .variantStream("facing=west,half=bottom,shape=inner_right", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder.rotationY(180)))
                .variantStream("facing=west,half=bottom,shape=outer_left", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder.rotationY(90)))
                .variantStream("facing=west,half=bottom,shape=outer_right", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder.rotationY(180)))
                .variantStream("facing=west,half=bottom,shape=straight", baseVariantStream(STAIRS)
                        .map(builder -> builder.rotationY(180)))
                .variantStream("facing=west,half=top,shape=inner_left", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(180)))
                .variantStream("facing=west,half=top,shape=inner_right", baseVariantStream(STAIRS_INNER)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(270)))
                .variantStream("facing=west,half=top,shape=outer_left", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(180)))
                .variantStream("facing=west,half=top,shape=outer_right", baseVariantStream(STAIRS_OUTER)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(270)))
                .variantStream("facing=west,half=top,shape=straight", baseVariantStream(STAIRS)
                        .map(builder -> builder
                                .rotationX(180)
                                .rotationY(180)));
    }

    private Stream<Variant.Builder> baseVariantStream(ModelFile type) {
        return variants.stream()
                .map(Variant.Builder::new)
                .map(builder -> builder
                        .modelType(type)
                        .uvLock(true));
    }
}
