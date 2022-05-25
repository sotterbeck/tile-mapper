package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.resourcepack.Resource;
import de.simbuildings.tilemapper.variations.blockstate.BlockState;
import de.simbuildings.tilemapper.variations.model.Model;

import java.util.Set;
import java.util.function.Function;

public enum BlockType {
    BLOCK(
            BlockState::createBlock,
            resource -> Set.of(Model.createBlock(resource, resource)),
            ""),
    STAIRS(
            BlockState::createStairs,
            resource -> Model.createStairs(resource, resource, resource, resource),
            "stairs"),
    SLAB(
            BlockState::createSlab,
            resource -> Model.createSlab(resource, resource, resource, resource),
            "slab");

    private final Function<Set<Variant.Builder>, BlockState> blockStateFactory;
    private final Function<Resource, Set<Model>> modelFactory;
    private final String blockStateSuffix;

    BlockType(Function<Set<Variant.Builder>, BlockState> blockStateFactory, Function<Resource, Set<Model>> modelFactory, String blockStateSuffix) {
        this.blockStateFactory = blockStateFactory;
        this.modelFactory = modelFactory;
        this.blockStateSuffix = blockStateSuffix;
    }

    public BlockState blockState(Set<Variant.Builder> variants) {
        return blockStateFactory.apply(variants);
    }

    public Set<Model> model(Resource resource) {
        return modelFactory.apply(resource);
    }

    public String blockStateSuffix() {
        return blockStateSuffix.isEmpty()
                ? ""
                : "_" + blockStateSuffix;
    }
}
