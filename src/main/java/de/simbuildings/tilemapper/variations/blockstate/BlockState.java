package de.simbuildings.tilemapper.variations.blockstate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import de.simbuildings.tilemapper.common.ExportResource;
import de.simbuildings.tilemapper.variations.BlockType;
import de.simbuildings.tilemapper.variations.Variant;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class BlockState implements ExportResource {
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

    @Override
    public Path outputFile(BlockType blockType) {
        return variantMap.values().stream()
                .flatMap(Collection::stream)
                .map(Variant::resource)
                .map(resource -> resource.blockStateFile(blockType))
                .findFirst()
                .orElseThrow();
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
