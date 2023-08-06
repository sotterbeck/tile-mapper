package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.DefaultResourcepackPathProvider;
import de.simbuildings.tilemapper.core.resourcepack.Resource;

public class BlockStateVariantBuilder {

    private Resource resource;

    private ModelFile modelFile = ModelFile.BLOCK;
    private int weight;
    private boolean uvLock;
    private int x;
    private int y;
    private String namespace = "minecraft";

    public BlockStateVariantBuilder(BlockStateVariantBuilder builder) {
        this.resource = builder.resource.withNamespace(builder.namespace);
        this.modelFile = builder.modelFile;
        this.weight = builder.weight;
        this.uvLock = builder.uvLock;
        this.x = builder.x;
        this.y = builder.y;
        this.namespace = builder.namespace;
    }

    public BlockStateVariantBuilder(Resource resource) {
        this.resource = resource;
    }

    public BlockStateVariantBuilder weight(int weight) {
        this.weight = weight;
        return this;
    }

    public BlockStateVariantBuilder modelType(ModelFile type) {
        this.modelFile = type;
        return this;
    }

    public BlockStateVariantBuilder uvLock(boolean lock) {
        this.uvLock = lock;
        return this;
    }

    public BlockStateVariantBuilder rotationX(int x) {
        if (isValidRotation(x)) {
            throw new IllegalArgumentException("rotation must be in increments of 90");
        }
        this.x = x;
        return this;
    }

    public BlockStateVariantBuilder rotationY(int y) {
        if (isValidRotation(y)) {
            throw new IllegalArgumentException("rotation must be in increments of 90");
        }
        this.y = y;
        return this;
    }

    public BlockStateVariantBuilder namespace(String namespace) {
        this.namespace = namespace;
        this.resource = resource.withNamespace(namespace);
        return this;
    }

    public BlockStateVariant build() {
        return new JacksonBlockStateVariant(
                new DefaultResourcepackPathProvider(resource).modelLocation(modelFile),
                weight,
                uvLock,
                x,
                y
        );
    }

    private boolean isValidRotation(int rotation) {
        return rotation % 90 != 0;
    }
}
