package de.simbuildings.tilemapper.variations.blockstate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import de.simbuildings.tilemapper.variations.Variant;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class BlockState {
    private final Map<String, Set<Variant>> variantMap;

    private BlockState(Set<Variant.Builder> variants) {
        SortedSet<Variant> sortedVariants = variants.stream()
                .map(Variant.Builder::build)
                .collect(Collectors.toCollection(TreeSet::new));
        this.variantMap = Map.of("", sortedVariants);
    }

    private BlockState(Builder builder) {
        this.variantMap = builder.variantMap;
    }

    public static BlockState createBlock(Variant.Builder variant) {
        return new BlockState(Set.of(variant));
    }

    public static BlockState createBlock(Set<Variant.Builder> variants) {
        return new BlockState(variants);
    }

    public static BlockState createSlab(Set<Variant.Builder> variants) {
        return new SlabBlockStateFactory(variants).get();
    }

    public static BlockState createStairs(Set<Variant.Builder> variants) {
        return new StairsBlockStateFactory(variants).get();
    }

    @JsonGetter("variants")
    @JsonFormat(with = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    public Map<String, Set<Variant>> variants() {
        return Collections.unmodifiableMap(variantMap);
    }

    public static class Builder {
        private final Map<String, Set<Variant>> variantMap = new HashMap<>();

        public Builder variants(String variantName, Set<Variant> variants) {
            variantMap.put(variantName, new TreeSet<>(variants));
            return this;
        }

        public Builder variantStream(String variantName, Stream<Variant.Builder> variantBuilders) {
            variantMap.put(variantName, variantBuilders
                    .map(Variant.Builder::build)
                    .collect(toCollection(TreeSet::new)));
            return this;
        }

        public BlockState build() {
            return new BlockState(this);
        }
    }
}
