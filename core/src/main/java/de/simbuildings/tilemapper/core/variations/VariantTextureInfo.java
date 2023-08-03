package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides information about the textures that will be applied to the faces of models.
 * For example, a stair model may have different textures for its top, bottom, or sides.
 */
public final class VariantTextureInfo {
    private final Resource defaultResource;
    private final Map<ResourceKey, Resource> resourceMap;

    private VariantTextureInfo(Resource defaultResource) {
        this.defaultResource = defaultResource;
        resourceMap = Collections.emptyMap();
    }

    private VariantTextureInfo(Builder builder) {
        this.defaultResource = builder.defaultResource;
        this.resourceMap = builder.resourceMap;
    }

    /**
     * A static factory method to create a <code>VariantTextureInfo</code> object with a default texture.
     * @param defaultResource The texture that will be applied on all faces.
     * @return The <code>VariantTextureInfo</code> object with the default texture.
     */
    public static VariantTextureInfo of(Resource defaultResource) {
        return new VariantTextureInfo(defaultResource);
    }

    public Resource defaultResource() {
        return defaultResource;
    }

    public Resource getTexture(String blockType, String face) {
        return resourceMap.getOrDefault(new ResourceKey(blockType, face), defaultResource());
    }

    /**
     * Builder that allows the incremental creation of a <code>VariantTextureInfo</code> object.
     */
    public static class Builder {
        private Resource defaultResource;
        private Map<ResourceKey, Resource> resourceMap = new HashMap<>();

        public Builder(Resource defaultResource) {
            this.defaultResource = defaultResource;
        }

        private static Map<ResourceKey, Resource> replaceVariants(Map<ResourceKey, Resource> map, String namespace) {
            if (map.isEmpty()) {
                return Collections.emptyMap();
            }
            Map<ResourceKey, Resource> temporaryMap = new HashMap<>(map);
            for (Map.Entry<ResourceKey, Resource> entry : temporaryMap.entrySet()) {
                temporaryMap.replace(entry.getKey(), entry.getValue().withNamespace(namespace));
            }
            return temporaryMap;
        }

        public Builder addTexture(String blockType, String face, Resource texture) {
            resourceMap.put(new ResourceKey(blockType, face), texture);
            return this;
        }

        Builder namespace(String namespace) {
            this.defaultResource = defaultResource.withNamespace(namespace);
            this.resourceMap = replaceVariants(resourceMap, namespace);
            return this;
        }

        public VariantTextureInfo build() {
            return new VariantTextureInfo(this);
        }
    }

    private record ResourceKey(String blockType, String face) {
    }
}
