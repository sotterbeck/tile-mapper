package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;
import de.simbuildings.tilemapper.core.variations.model.Face;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

final class VariantTextureInfo {
    private final Resource defaultResource;

    private final Map<Face, Resource> slabResources;
    private final Map<Face, Resource> stairResources;

    public VariantTextureInfo(Resource defaultResource) {
        this.defaultResource = defaultResource;
        slabResources = Collections.emptyMap();
        stairResources = Collections.emptyMap();
    }

    private VariantTextureInfo(Builder builder) {
        this.defaultResource = builder.defaultResource;
        this.slabResources = builder.slabResources;
        this.stairResources = builder.stairResources;

    }

    public Resource defaultResource() {
        return defaultResource;
    }

    public Resource slabResource(Face face) {
        return Objects.requireNonNullElse(slabResources.get(face), defaultResource);
    }

    public Resource stairResource(Face face) {
        return Objects.requireNonNullElse(stairResources.get(face), defaultResource);
    }

    public static class Builder {
        private Resource defaultResource;
        private Map<Face, Resource> slabResources = new EnumMap<>(Face.class);
        private Map<Face, Resource> stairResources = new EnumMap<>(Face.class);

        public Builder(Resource defaultResource) {
            this.defaultResource = defaultResource;
        }

        public VariantTextureInfo.Builder slabResource(Face face, Resource resource) {
            slabResources.put(face, resource);
            return this;
        }

        public Builder stairResource(Face face, Resource resource) {
            stairResources.put(face, resource);
            return this;
        }

        Builder slabResourceMap(Map<Face, Resource> resourceMap) {
            slabResources = resourceMap;
            return this;
        }

        Builder stairResourceMap(Map<Face, Resource> resourceMap) {
            stairResources = resourceMap;
            return this;
        }

        Builder namespace(String namespace) {
            this.defaultResource = defaultResource.withNamespace(namespace);
            this.slabResources = replaceVariants(slabResources, namespace);
            this.stairResources = replaceVariants(stairResources, namespace);
            return this;
        }

        private static Map<Face, Resource> replaceVariants(Map<Face, Resource> map, String namespace) {
            if (map.isEmpty()) {
                return Collections.emptyMap();
            }
            Map<Face, Resource> temporaryMap = new EnumMap<>(map);
            for (Map.Entry<Face, Resource> entry : temporaryMap.entrySet()) {
                temporaryMap.replace(entry.getKey(), entry.getValue().withNamespace(namespace));
            }
            return temporaryMap;
        }

        public VariantTextureInfo build() {
            return new VariantTextureInfo(this);
        }
    }
}
