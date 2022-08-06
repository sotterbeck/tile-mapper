package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.common.Converter;
import de.simbuildings.tilemapper.resourcepack.Resource;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

class ResourceVariantConverter implements Converter<VariantDto, ResourceVariant> {

    private final String material;
    private final String namespace;
    private final BiFunction<String, Integer, String> renameFunction;

    public ResourceVariantConverter(String material, BiFunction<String, Integer, String> renameFunction) {
        this.material = material;
        this.namespace = Resource.DEFAULT_NAMESPACE;
        this.renameFunction = renameFunction;
    }

    public ResourceVariantConverter(String material, BiFunction<String, Integer, String> renameFunction, String namespace) {
        this.material = material;
        this.namespace = namespace;
        this.renameFunction = renameFunction;
    }

    @Override
    public ResourceVariant fromDto(VariantDto dto) {
        return dto.resourceAt(material, 0, renameFunction, namespace);
    }

    @Override
    public Set<ResourceVariant> fromDtos(Collection<VariantDto> dtos) {
        Set<ResourceVariant> resourceVariants = new HashSet<>();
        VariantDto[] indexedVariantDtos = dtos.toArray(VariantDto[]::new);
        for (int i = 0; i < indexedVariantDtos.length; i++) {
            ResourceVariant resourceVariant = indexedVariantDtos[i].resourceAt(material, i, renameFunction, namespace);
            resourceVariants.add(resourceVariant);
        }
        return Collections.unmodifiableSet(resourceVariants);
    }
}
