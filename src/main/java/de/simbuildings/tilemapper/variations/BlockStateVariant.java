package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.variations.model.ModelFile;

import java.util.Objects;

/**
 * Represents a variant in a block state
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class BlockStateVariant implements Comparable<BlockStateVariant> {
    private final String model;
    private final Resource resource;

    private final int weight;
    private final boolean uvLock;
    private final int x;
    private final int y;

    private BlockStateVariant(Builder builder) {
        this.model = builder.resource.modelLocation(builder.modelFile);
        this.resource = builder.resource;

        this.weight = builder.weight;
        this.uvLock = builder.uvLock;
        this.x = builder.x;
        this.y = builder.y;
    }

    public Resource resource() {
        return resource;
    }

    @JsonGetter
    public int weight() {
        return weight;
    }

    @JsonGetter("uvlock")
    public boolean uvLock() {
        return uvLock;
    }

    @JsonGetter("x")
    public int rotationX() {
        return x;
    }

    @JsonGetter("y")
    public int rotationY() {
        return y;
    }

    @JsonGetter
    public String model() {
        return model;
    }

    public String namespace() {
        return resource.namespace();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockStateVariant variant = (BlockStateVariant) o;
        return weight == variant.weight && x == variant.x && y == variant.y && model.equals(variant.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, weight, x, y);
    }

    @Override
    public int compareTo(BlockStateVariant other) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.model, other.model);
    }

    @Override
    public String toString() {
        return "Variant{" +
               "model='" + model + '\'' +
               ", weight=" + weight +
               ", uvLock=" + uvLock +
               ", x=" + x +
               ", y=" + y +
               '}';
    }

    public static class Builder {
        private Resource resource;

        private ModelFile modelFile = ModelFile.BLOCK;
        private int weight;
        private boolean uvLock;
        private int x;
        private int y;
        private String namespace = "minecraft";

        public Builder(Builder builder) {
            this.resource = builder.resource.withNamespace(builder.namespace);
            this.modelFile = builder.modelFile;
            this.weight = builder.weight;
            this.uvLock = builder.uvLock;
            this.x = builder.x;
            this.y = builder.y;
            this.namespace = builder.namespace;
        }

        public Builder(Resource resource) {
            this.resource = resource;
        }

        public Builder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder modelType(ModelFile type) {
            this.modelFile = type;
            return this;
        }

        public Builder uvLock(boolean lock) {
            this.uvLock = lock;
            return this;
        }

        public Builder rotationX(int x) {
            if (isValidRotation(x)) {
                throw new IllegalArgumentException("rotation must be in increments of 90");
            }
            this.x = x;
            return this;
        }

        public Builder rotationY(int y) {
            if (isValidRotation(y)) {
                throw new IllegalArgumentException("rotation must be in increments of 90");
            }
            this.y = y;
            return this;
        }

        public Builder namespace(String namespace) {
            this.namespace = namespace;
            this.resource = resource.withNamespace(namespace);
            return this;
        }

        public BlockStateVariant build() {
            return new BlockStateVariant(this);
        }

        private boolean isValidRotation(int rotation) {
            return rotation % 90 != 0;
        }
    }
}