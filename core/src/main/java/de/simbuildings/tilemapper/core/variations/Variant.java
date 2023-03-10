package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.image.TextureImage;
import de.simbuildings.tilemapper.core.resourcepack.Resource;
import de.simbuildings.tilemapper.core.variations.model.Face;

import java.util.*;
import java.util.function.BiFunction;

import static java.util.stream.Collectors.toUnmodifiableMap;
//TODO: add extensive documentation
public final class Variant implements Comparable<Variant> {
    private final TextureImage defaultTexture;
    private final int weight;
    private final Map<Face, TextureImage> slabTextures;
    private final Map<Face, TextureImage> stairTextures;

    public static Builder builder(TextureImage texture) {
        return new Builder(texture);
    }

    public Variant(TextureImage texture, int weight) {
        this.defaultTexture = texture;
        this.weight = weight;
        slabTextures = Collections.emptyMap();
        stairTextures = Collections.emptyMap();
    }

    private Variant(Builder builder) {
        this.defaultTexture = builder.textureImage;
        this.weight = builder.weight;
        this.slabTextures = builder.slabTextures;
        this.stairTextures = builder.stairTextures;
    }

    public Variant(TextureImage texture) {
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

    public Variant withWeight(int weight) {
        return builder(defaultTexture)
                .stairTextureMap(stairTextures)
                .slabTextureMap(slabTextures)
                .weight(weight)
                .build();
    }

    VariantTextureInfo textureInfoAt(String material, int index, BiFunction<String, Integer, String> renameFunction, String namespace) {
        Resource defaultResource = new Resource(material, renameFunction.apply(material, index));

        Map<Face, Resource> slabResources = faceTexturesToResources(material, slabTextures);
        Map<Face, Resource> stairResources = faceTexturesToResources(material, stairTextures);
        return new VariantTextureInfo.Builder(defaultResource)
                .slabResourceMap(slabResources)
                .stairResourceMap(stairResources)
                .namespace(namespace)
                .build();
    }

    VariantTextureInfo textureInfoAt(String material, int index, BiFunction<String, Integer, String> renameFunction) {
        return textureInfoAt(material, index, renameFunction, Resource.DEFAULT_NAMESPACE);
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
        var that = (Variant) obj;
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

    @Override
    public int compareTo(Variant other) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.defaultTexture.name(), other.defaultTexture.name());
    }

    public static class Builder {
        private final TextureImage textureImage;
        private final Map<Face, TextureImage> slabTextures = new EnumMap<>(Face.class);
        private final Map<Face, TextureImage> stairTextures = new EnumMap<>(Face.class);

        private int weight;

        Builder(TextureImage textureImage) {
            this.textureImage = textureImage;
        }


        public Builder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder slabTexture(Face face, TextureImage texture) {
            slabTextures.put(face, texture);
            return this;
        }

        public Builder stairTexture(Face face, TextureImage texture) {
            stairTextures.put(face, texture);
            return this;
        }

        private Builder slabTextureMap(Map<Face, TextureImage> textureImageMap) {
            slabTextures.putAll(textureImageMap);
            return this;
        }

        private Builder stairTextureMap(Map<Face, TextureImage> textureImageMap) {
            stairTextures.putAll(textureImageMap);
            return this;
        }

        public Variant build() {
            return new Variant(this);
        }
    }
}
