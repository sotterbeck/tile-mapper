package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.common.Converter;
import de.simbuildings.tilemapper.core.resourcepack.Resource;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

class VariantTextureInfoConverter implements Converter<Variant, VariantTextureInfo> {

    private final String material;
    private final String namespace;
    private final BiFunction<String, Integer, String> renameFunction;

    public VariantTextureInfoConverter(String material, BiFunction<String, Integer, String> renameFunction) {
        this.material = material;
        this.namespace = Resource.DEFAULT_NAMESPACE;
        this.renameFunction = renameFunction;
    }

    public VariantTextureInfoConverter(String material, BiFunction<String, Integer, String> renameFunction, String namespace) {
        this.material = material;
        this.namespace = namespace;
        this.renameFunction = renameFunction;
    }

    @Override
    public VariantTextureInfo fromDto(Variant variant) {
        return variant.textureInfoAt(material, 0, renameFunction, namespace);
    }

    @Override
    public Set<VariantTextureInfo> fromDtos(Collection<Variant> variants) {
        Set<VariantTextureInfo> variantTextureInfos = new HashSet<>();
        Variant[] indexedVariants = variants.toArray(Variant[]::new);
        for (int i = 0; i < indexedVariants.length; i++) {
            VariantTextureInfo variantTextureInfo = indexedVariants[i].textureInfoAt(material, i, renameFunction, namespace);
            variantTextureInfos.add(variantTextureInfo);
        }
        return Collections.unmodifiableSet(variantTextureInfos);
    }
}
