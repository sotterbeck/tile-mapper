package de.simbuildings.tilemapper.variations.blockstate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import de.simbuildings.tilemapper.variations.Variant;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class BlockState {
    private final Map<String, Set<Variant>> variantMap;
    private final String fileSuffix;

    private BlockState(Set<Variant.Builder> variants) {
        SortedSet<Variant> sortedVariants = variants.stream()
                .map(Variant.Builder::build)
                .collect(Collectors.toCollection(TreeSet::new));
        this.variantMap = Map.of("", sortedVariants);
        this.fileSuffix = "";
    }

    private BlockState(Builder builder) {
        this.variantMap = builder.variantMap;
        this.fileSuffix = builder.fileSuffix;
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

    public Path resourcepackLocation(String material) {
        return Paths.get("assets", "minecraft", "blockstates", material + fileSuffix + ".json");
    }

    public static class Builder {
        private final Map<String, Set<Variant>> variantMap = new HashMap<>();
        private String fileSuffix = "";

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

        public Builder fileSuffix(String suffix) {
            fileSuffix = "_" + suffix;
            return this;
        }

        public BlockState build() {
            return new BlockState(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Builder builder = (Builder) o;
            return Objects.equals(variantMap, builder.variantMap);
        }

        @Override
        public int hashCode() {
            return Objects.hash(variantMap);
        }
    }
}
