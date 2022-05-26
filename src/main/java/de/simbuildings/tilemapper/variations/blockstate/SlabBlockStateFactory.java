package de.simbuildings.tilemapper.variations.blockstate;

import de.simbuildings.tilemapper.variations.Variant;
import de.simbuildings.tilemapper.variations.model.ModelFile;

import java.util.Set;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toUnmodifiableSet;

class SlabBlockStateFactory implements Supplier<BlockState> {

    private final Set<Variant.Builder> bottomVariants;
    private final Set<Variant.Builder> doubleVariants;
    private final Set<Variant.Builder> topVariants;

    public SlabBlockStateFactory(Set<Variant.Builder> variants) {
        this.bottomVariants = variants;
        this.doubleVariants = variants;
        this.topVariants = variants;
    }

    public BlockState get() {
        return new BlockState.Builder()
                .fileSuffix("slab")
                .variants("type=bottom", bottomVariants.stream()
                        .map(builder -> builder.modelType(ModelFile.SLAB))
                        .map(Variant.Builder::build)
                        .collect(toUnmodifiableSet()))
                .variants("type=double", doubleVariants.stream()
                        .map(builder -> builder.modelType(ModelFile.BLOCK))
                        .map(Variant.Builder::build)
                        .collect(toUnmodifiableSet()))
                .variants("type=top", topVariants.stream()
                        .map(builder -> builder.modelType(ModelFile.SLAB_TOP))
                        .map(Variant.Builder::build)
                        .collect(toUnmodifiableSet()))
                .build();
    }
}
