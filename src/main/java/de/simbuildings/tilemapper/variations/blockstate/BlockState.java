package de.simbuildings.tilemapper.variations.blockstate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.variations.BlockStateVariant;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class BlockState {
    private final Map<String, Set<BlockStateVariant>> variantMap;
    private final String fileSuffix;

    private BlockState(Set<BlockStateVariant.Builder> variants) {
        SortedSet<BlockStateVariant> sortedBlockStateVariants = variants.stream()
                .map(BlockStateVariant.Builder::build)
                .collect(Collectors.toCollection(TreeSet::new));
        this.variantMap = Map.of("", sortedBlockStateVariants);
        this.fileSuffix = "";
    }

    private BlockState(Builder builder) {
        this.variantMap = builder.variantMap;
        this.fileSuffix = builder.fileSuffix;
    }

    public static BlockState createBlock(BlockStateVariant.Builder variant) {
        return new BlockState(Set.of(variant));
    }

    public static BlockState createBlock(Set<BlockStateVariant.Builder> variants) {
        return new BlockState(variants);
    }

    public static BlockState createSlab(Set<BlockStateVariant.Builder> variants) {
        return new SlabBlockStateFactory(variants).get();
    }

    public static BlockState createStairs(Set<BlockStateVariant.Builder> variants) {
        return new StairsBlockStateFactory(variants).get();
    }

    @JsonGetter("variants")
    @JsonFormat(with = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    public Map<String, Set<BlockStateVariant>> variants() {
        return Collections.unmodifiableMap(variantMap);
    }

    public Path resourcepackLocation(Resource resource) {
        return resource.blockStateDirectory(fileSuffix);
    }

    public static class Builder {
        private final Map<String, Set<BlockStateVariant>> variantMap = new HashMap<>();
        private String fileSuffix = "";

        public Builder variants(String variantName, Set<BlockStateVariant> variants) {
            variantMap.put(variantName, new TreeSet<>(variants));
            return this;
        }

        public Builder variantStream(String variantName, Stream<BlockStateVariant.Builder> variantBuilders) {
            variantMap.put(variantName, variantBuilders
                    .map(BlockStateVariant.Builder::build)
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
