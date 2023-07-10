package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Creates a <Code>BlockState</Code> and <code>Models</code> for a block.
 */
public final class BlockJsonFactory implements ResourcePackJsonFactory {
    @Override
    public BlockState blockState(Set<BlockStateVariantBuilder> variants) {
        Set<BlockStateVariant> builtVariants = variants.stream()
                .map(BlockStateVariantBuilder::build)
                .collect(Collectors.toCollection(TreeSet::new));
        return new JacksonBlockState.Builder()
                .variants("", builtVariants)
                .build();
    }

    @Override
    public Set<Model> models(Resource model, Resource bottom, Resource top, Resource side) {
        VariantTextureInfo variantTextureInfo = new VariantTextureInfo.Builder(bottom)
                .addTexture("block", "bottom", bottom)
                .addTexture("block", "top", top)
                .addTexture("block", "side", side)
                .build();
        return models(model, variantTextureInfo);
    }

    @Override
    public Set<Model> models(Resource modelResource, VariantTextureInfo textures) {
        Model blockModel = new JacksonModel.Builder(ModelFile.BLOCK, modelResource)
                .texture("all", textures.defaultResource())
                .build();
        return Set.of(blockModel);
    }
}
