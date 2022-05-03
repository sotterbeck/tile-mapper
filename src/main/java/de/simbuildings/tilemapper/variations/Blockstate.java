package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Map;
import java.util.Set;

public class Blockstate {
    private final Map<String, Set<Variant>> variant;

    private Blockstate(Set<Variant> variants) {
        this.variant = Map.of("", variants);
    }

    public static Blockstate of(Variant variant) {
        return new Blockstate(Set.of(variant));
    }

    public static Blockstate of(Set<Variant> variants) {
        return new Blockstate(variants);
    }

    @JsonGetter("variants")
    @JsonFormat(with = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    public Map<String, Set<Variant>> variants() {
        return variant;
    }
}
