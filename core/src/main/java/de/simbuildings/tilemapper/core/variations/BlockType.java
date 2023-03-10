package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;
import de.simbuildings.tilemapper.core.variations.blockstate.BlockState;
import de.simbuildings.tilemapper.core.variations.model.Face;
import de.simbuildings.tilemapper.core.variations.model.Model;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Represents a block model type for which block state and model files can be created.
 */
public enum BlockType {
    BLOCK(BlockState::createBlock,
            (modelResource, resourceVariant) -> Set.of(Model.createBlock(modelResource, resourceVariant.defaultResource()))
    ),
    SLAB(BlockState::createSlab,
            (modelResource, resourceVariant) -> Model.createSlab(
                    modelResource,
                    resourceVariant.slabResource(Face.BOTTOM),
                    resourceVariant.slabResource(Face.TOP),
                    resourceVariant.slabResource(Face.SIDE))
    ),
    STAIRS(BlockState::createStairs,
            (modelResource, resourceVariant) -> Model.createStairs(
                    modelResource,
                    resourceVariant.stairResource(Face.BOTTOM),
                    resourceVariant.stairResource(Face.TOP),
                    resourceVariant.stairResource(Face.SIDE)
            )
    );

    private final Function<Set<BlockStateVariant.Builder>, BlockState> blockStateFactory;
    private final BiFunction<Resource, VariantTextureInfo, Set<Model>> modelFactory;

    BlockType(Function<Set<BlockStateVariant.Builder>, BlockState> blockStateFactory, BiFunction<Resource, VariantTextureInfo, Set<Model>> modelFactory) {
        this.blockStateFactory = blockStateFactory;
        this.modelFactory = modelFactory;
    }

    /**
     * Creates a block state for the specified variants.
     *
     * @param variants the differed model variants that the block state contains
     * @return the block state for this block type
     */
    BlockState createBlockState(Set<BlockStateVariant.Builder> variants) {
        return blockStateFactory.apply(variants);
    }

    /**
     * Creates the models with the specified resources.
     *
     * @param modelResource      the location of the blockstate and default resource
     * @param variantTextureInfo the textures that will be applied on the model
     * @return multiple models for this block type
     */
    Set<Model> createModels(Resource modelResource, VariantTextureInfo variantTextureInfo) {
        return modelFactory.apply(modelResource, variantTextureInfo);
    }
}
