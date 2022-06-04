package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.variations.model.Face;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

final class ResourceVariant {
    private final Resource defaultResource;

    private final Map<Face, Resource> slabResources;
    private final Map<Face, Resource> stairResources;

    public ResourceVariant(Resource defaultResource) {
        this.defaultResource = defaultResource;
        slabResources = Collections.emptyMap();
        stairResources = Collections.emptyMap();
    }

    private ResourceVariant(Builder builder) {
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
        private final Resource defaultResource;
        private Map<Face, Resource> slabResources = new EnumMap<>(Face.class);
        private Map<Face, Resource> stairResources = new EnumMap<>(Face.class);

        public Builder(Resource defaultResource) {
            this.defaultResource = defaultResource;
        }

        public ResourceVariant.Builder slabResource(Face face, Resource resource) {
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

        public ResourceVariant build() {
            return new ResourceVariant(this);
        }
    }
}
