package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Represents a block model type for which block state and model files can be created.
 */
public enum BlockType {
    BLOCK(
            blockStateVariantBuilders -> new BlockJsonFactory().blockState(blockStateVariantBuilders),
            (modelResource, resourceVariant)
                    -> new BlockJsonFactory().models(modelResource, resourceVariant)
    ),
    SLAB(
            blockStateVariantBuilders -> new SlabJsonFactory().blockState(blockStateVariantBuilders),
            (modelResource, resourceVariant)
                    -> new SlabJsonFactory().models(modelResource, resourceVariant)
    ),
    STAIRS(
            blockStateVariantBuilders -> new StairsJsonFactory().blockState(blockStateVariantBuilders),
            (modelResource, resourceVariant) -> new StairsJsonFactory().models(modelResource, resourceVariant)
    );

    private final Function<Set<BlockStateVariantBuilder>, BlockState> blockStateFactory;
    private final BiFunction<Resource, VariantTextureInfo, Set<Model>> modelFactory;

    BlockType(Function<Set<BlockStateVariantBuilder>, BlockState> blockStateFactory, BiFunction<Resource, VariantTextureInfo, Set<Model>> modelFactory) {
        this.blockStateFactory = blockStateFactory;
        this.modelFactory = modelFactory;
    }

    /**
     * Creates a block state for the specified variants.
     *
     * @param variants the differed model variants that the block state contains
     * @return the block state for this block type
     */
    BlockState createBlockState(Set<BlockStateVariantBuilder> variants) {
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
