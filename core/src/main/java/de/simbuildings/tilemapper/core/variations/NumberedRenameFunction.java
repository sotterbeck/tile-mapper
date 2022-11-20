package de.simbuildings.tilemapper.core.variations;

import java.util.function.BiFunction;

class NumberedRenameFunction implements BiFunction<String, Integer, String> {
    @Override
    public String apply(String material, Integer index) {
        return "%s_%d".formatted(material, index + 1);
    }
}
