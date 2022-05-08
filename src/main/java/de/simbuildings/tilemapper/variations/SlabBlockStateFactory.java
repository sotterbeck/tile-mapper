package de.simbuildings.tilemapper.variations;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SlabBlockStateFactory implements Supplier<BlockState> {

    private final Set<Variant.Builder> bottomVariants;
    private final Set<Variant.Builder> doubleVariants;
    private final Set<Variant.Builder> topVariants;

    public SlabBlockStateFactory(Set<Variant.Builder> bottomVariants, Set<Variant.Builder> doubleVariants, Set<Variant.Builder> topVariants) {
        this.bottomVariants = bottomVariants;
        this.doubleVariants = doubleVariants;
        this.topVariants = topVariants;
    }

    public SlabBlockStateFactory(Set<Variant.Builder> variants) {
        this.bottomVariants = variants;
        this.doubleVariants = variants;
        this.topVariants = variants;
    }

    public BlockState get() {
        return new BlockState.Builder()
                .namedVariants("type=bottom", bottomVariants.stream()
                        .map(builder -> builder.modelType(ModelType.SLAB))
                        .map(Variant.Builder::build)
                        .collect(Collectors.toUnmodifiableSet()))
                .namedVariants("type=double", doubleVariants.stream()
                        .map(builder -> builder.modelType(ModelType.BLOCK))
                        .map(Variant.Builder::build)
                        .collect(Collectors.toUnmodifiableSet()))
                .namedVariants("type=top", topVariants.stream()
                        .map(builder -> builder.modelType(ModelType.SLAB_TOP))
                        .map(Variant.Builder::build)
                        .collect(Collectors.toUnmodifiableSet()))
                .build();
    }
}
