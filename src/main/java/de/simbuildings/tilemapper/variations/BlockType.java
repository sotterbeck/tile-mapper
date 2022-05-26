package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.variations.blockstate.BlockState;
import de.simbuildings.tilemapper.variations.model.Model;

import java.util.Set;
import java.util.function.Function;

public enum BlockType {
    BLOCK(
            BlockState::createBlock,
            resource -> Set.of(Model.createBlock(resource, resource))
    ),
    STAIRS(
            BlockState::createStairs,
            resource -> Model.createStairs(resource, resource, resource, resource)
    ),
    SLAB(
            BlockState::createSlab,
            resource -> Model.createSlab(resource, resource, resource, resource)
    );

    private final Function<Set<Variant.Builder>, BlockState> blockStateFactory;
    private final Function<Resource, Set<Model>> modelFactory;

    BlockType(Function<Set<Variant.Builder>, BlockState> blockStateFactory, Function<Resource, Set<Model>> modelFactory) {
        this.blockStateFactory = blockStateFactory;
        this.modelFactory = modelFactory;
    }

    public BlockState createBlockState(Set<Variant.Builder> variants) {
        return blockStateFactory.apply(variants);
    }

    public Set<Model> createModels(Resource resource) {
        return modelFactory.apply(resource);
    }
}
