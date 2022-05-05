package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.*;

public class BlockState {
    private final Map<String, Set<Variant>> variantMap;

    private BlockState(Set<Variant> variants) {
        Set<Variant> sortedVariants = new TreeSet<>(variants);
        this.variantMap = Map.of("", sortedVariants);
    }

    private BlockState(Builder builder) {
        this.variantMap = builder.variantMap;
    }

    public static BlockState ofDefaultVariantName(Variant variant) {
        return new BlockState(Set.of(variant));
    }

    public static BlockState ofDefaultVariantName(Set<Variant> variants) {
        return new BlockState(variants);
    }

    @JsonGetter("variants")
    @JsonFormat(with = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    public Map<String, Set<Variant>> variants() {
        return Collections.unmodifiableMap(variantMap);
    }

    public static class Builder {
        private final Map<String, Set<Variant>> variantMap = new HashMap<>();

        public Builder namedVariant(String variantName, Set<Variant> variants) {
            variantMap.put(variantName, variants);
            return this;
        }

        public Builder namedVariant(String variantName, Variant variant) {
            variantMap.put(variantName, Set.of(variant));
            return this;
        }

        public BlockState build() {
            return new BlockState(this);
        }
    }
}
