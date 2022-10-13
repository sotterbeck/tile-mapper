package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.common.Converter;
import de.simbuildings.tilemapper.image.TextureImage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

class NamedTextureImageConverter implements Converter<Variant, TextureImage> {
    private final String material;
    private final BiFunction<String, Integer, String> renameFunction;
    private final int index;

    public NamedTextureImageConverter(String material, BiFunction<String, Integer, String> renameFunction, int index) {
        this.material = material;
        this.renameFunction = renameFunction;
        this.index = index;
    }

    public NamedTextureImageConverter(String material, BiFunction<String, Integer, String> renameFunction) {
        this(material, renameFunction, 0);
    }

    private TextureImage renameTexture(Variant variant, int index1) {
        return variant.defaultTexture().withName(renameFunction.apply(material, index1));
    }

    @Override
    public TextureImage fromDto(Variant variant) {
        return renameTexture(variant, index);
    }

    @Override
    public Set<TextureImage> fromDtos(Collection<Variant> variants) {
        Set<TextureImage> namedImages = new HashSet<>();
        Variant[] indexVariants = variants.toArray(Variant[]::new);
        for (int i = 0; i < indexVariants.length; i++) {
            TextureImage texture = renameTexture(indexVariants[i], i);
            namedImages.add(texture);
        }
        return Collections.unmodifiableSet(namedImages);
    }
}
