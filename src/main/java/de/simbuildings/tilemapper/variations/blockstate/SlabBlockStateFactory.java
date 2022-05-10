package de.simbuildings.tilemapper.variations.blockstate;

import de.simbuildings.tilemapper.variations.Variant;
import de.simbuildings.tilemapper.variations.model.ModelType;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SlabBlockStateFactory implements Supplier<BlockState> {

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
                .namedVariants("type=bottom", bottomVariants.stream()
                        .map(builder -> builder.modelType(ModelType.SLAB))
                        .map(Variant.Builder::build)
                        .collect(sortedSetCollector()))
                .namedVariants("type=double", doubleVariants.stream()
                        .map(builder -> builder.modelType(ModelType.BLOCK))
                        .map(Variant.Builder::build)
                        .collect(sortedSetCollector()))
                .namedVariants("type=top", topVariants.stream()
                        .map(builder -> builder.modelType(ModelType.SLAB_TOP))
                        .map(Variant.Builder::build)
                        .collect(sortedSetCollector()))
                .build();
    }

    private Collector<Variant, ?, TreeSet<Variant>> sortedSetCollector() {
        return Collectors.toCollection(TreeSet::new);
    }
}
