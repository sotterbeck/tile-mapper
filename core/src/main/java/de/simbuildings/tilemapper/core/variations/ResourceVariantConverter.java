package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.common.Converter;
import de.simbuildings.tilemapper.core.resourcepack.Resource;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

class ResourceVariantConverter implements Converter<Variant, ResourceVariant> {

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
    public ResourceVariant fromDto(Variant variant) {
        return variant.resourceAt(material, 0, renameFunction, namespace);
    }

    @Override
    public Set<ResourceVariant> fromDtos(Collection<Variant> variants) {
        Set<ResourceVariant> resourceVariants = new HashSet<>();
        Variant[] indexedVariants = variants.toArray(Variant[]::new);
        for (int i = 0; i < indexedVariants.length; i++) {
            ResourceVariant resourceVariant = indexedVariants[i].resourceAt(material, i, renameFunction, namespace);
            resourceVariants.add(resourceVariant);
        }
        return Collections.unmodifiableSet(resourceVariants);
    }
}
