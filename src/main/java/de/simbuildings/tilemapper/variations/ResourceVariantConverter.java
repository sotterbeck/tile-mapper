package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.common.Converter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

class ResourceVariantConverter implements Converter<VariantDto, ResourceVariant> {

    private final String material;
    private final int index;
    private final BiFunction<String, Integer, String> renameFunction;

    public ResourceVariantConverter(String material, BiFunction<String, Integer, String> renameFunction) {
        this.material = material;
        this.renameFunction = renameFunction;
        this.index = 0;
    }

    public ResourceVariantConverter(String material, int index, BiFunction<String, Integer, String> renameFunction) {
        this.material = material;
        this.index = index;
        this.renameFunction = renameFunction;
    }

    @Override
    public ResourceVariant fromDto(VariantDto dto) {
        return dto.resourceAt(material, index, renameFunction);
    }

    @Override
    public Set<ResourceVariant> fromDtos(Collection<VariantDto> dtos) {
        Set<ResourceVariant> resourceVariants = new HashSet<>();
        VariantDto[] indexedVariantDtos = dtos.toArray(VariantDto[]::new);
        for (int i = 0; i < indexedVariantDtos.length; i++) {
            ResourceVariant resourceVariant = indexedVariantDtos[i].resourceAt(material, i, renameFunction);
            resourceVariants.add(resourceVariant);
        }
        return Collections.unmodifiableSet(resourceVariants);
    }
}
