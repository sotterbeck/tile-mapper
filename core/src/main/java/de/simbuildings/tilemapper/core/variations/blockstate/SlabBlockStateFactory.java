package de.simbuildings.tilemapper.core.variations.blockstate;

import de.simbuildings.tilemapper.core.variations.BlockStateVariant;
import de.simbuildings.tilemapper.core.variations.model.ModelFile;

import java.util.Set;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toUnmodifiableSet;

class SlabBlockStateFactory implements Supplier<BlockState> {

    private final Set<BlockStateVariant.Builder> bottomVariants;
    private final Set<BlockStateVariant.Builder> doubleVariants;
    private final Set<BlockStateVariant.Builder> topVariants;

    public SlabBlockStateFactory(Set<BlockStateVariant.Builder> variants) {
        this.bottomVariants = variants;
        this.doubleVariants = variants;
        this.topVariants = variants;
    }

    public BlockState get() {
        return new BlockState.Builder()
                .fileSuffix("slab")
                .variants("type=bottom", bottomVariants.stream()
                        .map(builder -> builder.modelType(ModelFile.SLAB))
                        .map(BlockStateVariant.Builder::build)
                        .collect(toUnmodifiableSet()))
                .variants("type=double", doubleVariants.stream()
                        .map(builder -> builder.modelType(ModelFile.BLOCK))
                        .map(BlockStateVariant.Builder::build)
                        .collect(toUnmodifiableSet()))
                .variants("type=top", topVariants.stream()
                        .map(builder -> builder.modelType(ModelFile.SLAB_TOP))
                        .map(BlockStateVariant.Builder::build)
                        .collect(toUnmodifiableSet()))
                .build();
    }
}
