package de.simbuildings.tilemapper.core.variations;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.simbuildings.tilemapper.core.resourcepack.Resource;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class JacksonBlockStateVariant implements BlockStateVariant {

    private final String model;
    private final Resource resource;

    private final int weight;
    private final boolean uvLock;
    private final int x;
    private final int y;

    JacksonBlockStateVariant(String model, Resource resource, int weight, boolean uvLock, int x, int y) {
        this.model = model;
        this.resource = resource;
        this.weight = weight;
        this.uvLock = uvLock;
        this.x = x;
        this.y = y;
    }

    @Override
    public Resource resource() {
        return resource;
    }

    @Override
    @JsonGetter
    public int weight() {
        return weight;
    }

    @Override
    @JsonGetter("uvlock")
    public boolean uvLock() {
        return uvLock;
    }

    @Override
    @JsonGetter("x")
    public int rotationX() {
        return x;
    }

    @Override
    @JsonGetter("y")
    public int rotationY() {
        return y;
    }

    @Override
    @JsonGetter
    public String model() {
        return model;
    }

    @Override
    public String namespace() {
        return resource.namespace();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JacksonBlockStateVariant variant = (JacksonBlockStateVariant) o;
        return weight == variant.weight && x == variant.x && y == variant.y && model.equals(variant.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, weight, x, y);
    }

    @Override
    public int compareTo(BlockStateVariant other) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.model(), other.model());
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
}