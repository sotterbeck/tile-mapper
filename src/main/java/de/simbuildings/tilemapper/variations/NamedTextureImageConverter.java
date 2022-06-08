package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.common.Converter;
import de.simbuildings.tilemapper.image.TextureImage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

class NamedTextureImageConverter implements Converter<VariantDto, TextureImage> {
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

    private TextureImage nameTextureImage(VariantDto dto, int index1) {
        return dto.defaultTexture().withName(renameFunction.apply(material, index1));
    }

    @Override
    public TextureImage fromDto(VariantDto dto) {
        return nameTextureImage(dto, index);
    }

    @Override
    public Set<TextureImage> fromDtos(Collection<VariantDto> dtos) {
        Set<TextureImage> namedImages = new HashSet<>();
        VariantDto[] indexVariantDtos = dtos.toArray(VariantDto[]::new);
        for (int i = 0; i < indexVariantDtos.length; i++) {
            TextureImage textureImage = nameTextureImage(indexVariantDtos[i], i);
            namedImages.add(textureImage);
        }
        return Collections.unmodifiableSet(namedImages);
    }
}
