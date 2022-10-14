package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.common.Converter;
import de.simbuildings.tilemapper.resourcepack.Resource;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

class BlockStateBuilderConverter implements Converter<Variant, BlockStateVariant.Builder> {

    private final String material;
    private final String namespace;
    private final BiFunction<String, Integer, String> renameFunction;

    public BlockStateBuilderConverter(String material, BiFunction<String, Integer, String> renameFunction) {
        this.material = material;
        this.namespace = Resource.DEFAULT_NAMESPACE;
        this.renameFunction = renameFunction;
    }

    public BlockStateBuilderConverter(String material, BiFunction<String, Integer, String> renameFunction, String namespace) {
        this.material = material;
        this.namespace = namespace;
        this.renameFunction = renameFunction;
    }

    @Override
    public BlockStateVariant.Builder fromDto(Variant variant) {
        Resource resource = variant.resourceAt(material, 0, renameFunction, namespace).defaultResource();
        return new BlockStateVariant.Builder(resource);
    }

    @Override
    public Set<BlockStateVariant.Builder> fromDtos(Collection<Variant> variants) {
        Set<BlockStateVariant.Builder> blockStateBuilders = new HashSet<>();
        Variant[] indexedDtos = variants.toArray(Variant[]::new);
        for (int i = 0; i < indexedDtos.length; i++) {
            Variant currentDto = indexedDtos[i];
            Resource resource = currentDto.resourceAt(material, i, renameFunction, namespace).defaultResource();
            blockStateBuilders.add(
                    new BlockStateVariant.Builder(resource)
                            .weight(currentDto.weight()));
        }
        return Collections.unmodifiableSet(blockStateBuilders);
    }
}
