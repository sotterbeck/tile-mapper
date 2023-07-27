package de.simbuildings.tilemapper.core.variations;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class JacksonBlockStateData implements BlockStateData {
    private final Map<String, Set<BlockStateVariant>> variantMap;

    private JacksonBlockStateData(Builder builder) {
        this.variantMap = builder.variantMap;
    }

    @Override
    @JsonGetter("variants")
    @JsonFormat(with = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    public Map<String, Set<BlockStateVariant>> variants() {
        return Collections.unmodifiableMap(variantMap);
    }

    public static class Builder implements BlockStateDataBuilder {
        private final Map<String, Set<BlockStateVariant>> variantMap = new HashMap<>();

        @Override
        public BlockStateDataBuilder variants(String variantName, Set<BlockStateVariant> variants) {
            variantMap.put(variantName, new TreeSet<>(variants));
            return this;
        }

        @Override
        public BlockStateDataBuilder variantStream(String variantName, Stream<BlockStateVariantBuilder> variantBuilders) {
            variantMap.put(variantName, variantBuilders
                    .map(BlockStateVariantBuilder::build)
                    .collect(toCollection(TreeSet::new)));
            return this;
        }

        @Override
        public BlockStateData build() {
            return new JacksonBlockStateData(this);
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
