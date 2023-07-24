package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;

import java.util.Set;

/**
 * Abstract Factory to create json used in Minecraft resource packs for a
 * given block type.
 */
public interface ResourcePackJsonFactory {

    default BlockState blockState(BlockStateVariantBuilder variant) {
        return blockState(Set.of(variant));
    }

    /**
     * Returns the <code>BlockState</code> for multiple <code>BlockStateVariant.Builder</code>s.
     *
     * @param variants the set of block state variant builders.
     * @return the BlockState for the given variants.
     */
    BlockState blockState(Set<BlockStateVariantBuilder> variants);

    /**
     * Returns multiple models with the specified textures.
     *
     * @param modelResource the resource location of the model.
     * @param textures      the textures that will be applied on the models faces.
     * @return multiple models using the specified textures.
     */
    Set<Model> models(Resource modelResource, VariantTextureInfo textures);
}
