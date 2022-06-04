package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.image.TextureImage;
import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.variations.model.Face;

import java.util.*;
import java.util.function.BiFunction;

import static java.util.stream.Collectors.toUnmodifiableMap;

public final class VariantDto {
    private final TextureImage defaultTexture;
    private final int weight;
    private final Map<Face, TextureImage> slabTextures;
    private final Map<Face, TextureImage> stairTextures;

    public static Builder builder(TextureImage texture) {
        return new Builder(texture);
    }

    public VariantDto(TextureImage texture, int weight) {
        this.defaultTexture = texture;
        this.weight = weight;
        slabTextures = Collections.emptyMap();
        stairTextures = Collections.emptyMap();
    }

    private VariantDto(Builder builder) {
        this.defaultTexture = builder.textureImage;
        this.weight = builder.weight;
        this.slabTextures = builder.slabTextures;
        this.stairTextures = builder.stairTextures;
    }

    public VariantDto(TextureImage texture) {
        this(texture, 0);
    }

    public TextureImage defaultTexture() {
        return defaultTexture;
    }

    public Set<TextureImage> additionalTextures() {
        return new HashSet<>(slabTextures.values());
    }

    public int weight() {
        return weight;
    }

    ResourceVariant resourceAt(String material, int index, BiFunction<String, Integer, String> renameFunction) {
        Resource defaultResource = new Resource(material, renameFunction.apply(material, index));

        Map<Face, Resource> slabResources = faceTexturesToResources(material, slabTextures);
        Map<Face, Resource> stairResources = faceTexturesToResources(material, stairTextures);
        return new ResourceVariant.Builder(defaultResource)
                .slabResourceMap(slabResources)
                .stairResourceMap(stairResources)
                .build();
    }

    private Map<Face, Resource> faceTexturesToResources(String material, Map<Face, TextureImage> textureMap) {
        return textureMap.entrySet().stream()
                .map(textureEntry -> new AbstractMap.SimpleImmutableEntry<>(textureEntry.getKey(),
                        new Resource(material, textureEntry.getValue().name())))
                .collect(toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public TextureImage slabTexture(Face face) {
        return Objects.requireNonNullElse(slabTextures.get(face), defaultTexture);
    }

    public TextureImage stairTexture(Face face) {
        return Objects.requireNonNullElse(stairTextures.get(face), defaultTexture);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (VariantDto) obj;
        return Objects.equals(this.defaultTexture, that.defaultTexture) &&
               this.weight == that.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(defaultTexture, weight);
    }

    @Override
    public String toString() {
        return "VariantDto[" +
               "texture=" + defaultTexture + ", " +
               "weight=" + weight + ']';
    }

    public static class Builder {
        private final TextureImage textureImage;
        private final Map<Face, TextureImage> slabTextures = new EnumMap<>(Face.class);
        private final Map<Face, TextureImage> stairTextures = new EnumMap<>(Face.class);

        private int weight;

        private Builder(TextureImage textureImage) {
            this.textureImage = textureImage;
        }


        public Builder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public VariantDto.Builder overrideSlabTexture(Face face, TextureImage texture) {
            slabTextures.put(face, texture);
            return this;
        }

        public Builder overrideStairTexture(Face face, TextureImage texture) {
            stairTextures.put(face, texture);
            return this;
        }

        public VariantDto build() {
            return new VariantDto(this);
        }
    }
}
