package de.simbuildings.tilemapper.core.variations;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import de.simbuildings.tilemapper.core.resourcepack.Resource;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class JacksonBlockState implements BlockState {
    private final Map<String, Set<BlockStateVariant>> variantMap;
    private final String fileSuffix;

    private JacksonBlockState(Set<BlockStateVariantBuilder> variants) {
        SortedSet<BlockStateVariant> sortedBlockStateVariants = variants.stream()
                .map(BlockStateVariantBuilder::build)
                .collect(Collectors.toCollection(TreeSet::new));
        this.variantMap = Map.of("", sortedBlockStateVariants);
        this.fileSuffix = "";
    }

    private JacksonBlockState(Builder builder) {
        this.variantMap = builder.variantMap;
        this.fileSuffix = builder.fileSuffix;
    }

    @Override
    @JsonGetter("variants")
    @JsonFormat(with = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    public Map<String, Set<BlockStateVariant>> variants() {
        return Collections.unmodifiableMap(variantMap);
    }

    @Override
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

        public Builder variantStream(String variantName, Stream<BlockStateVariantBuilder> variantBuilders) {
            variantMap.put(variantName, variantBuilders
                    .map(BlockStateVariantBuilder::build)
                    .collect(toCollection(TreeSet::new)));
            return this;
        }

        public Builder fileSuffix(String suffix) {
            fileSuffix = "_" + suffix;
            return this;
        }

        public BlockState build() {
            return new JacksonBlockState(this);
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
