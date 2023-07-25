package de.simbuildings.tilemapper.core.variations.blocks;

import de.simbuildings.tilemapper.core.resourcepack.Resource;
import de.simbuildings.tilemapper.core.variations.*;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Creates a <Code>BlockState</Code> and <code>Models</code> for a block.
 */
public final class BlockJsonFactory implements ResourcePackJsonFactory {

    @Override
    public BlockStateData blockState(Set<BlockStateVariantBuilder> variants) {
        Set<BlockStateVariant> builtVariants = variants.stream()
                .map(BlockStateVariantBuilder::build)
                .collect(Collectors.toCollection(TreeSet::new));
        return new JacksonBlockStateData.Builder()
                .variants("", builtVariants)
                .build();
    }

    @Override
    public Set<ModelData> models(Resource modelResource, VariantTextureInfo textures) {
        ModelData blockModelData = new JacksonModelData.Builder(ModelFile.BLOCK, modelResource)
                .texture("all", textures.defaultResource())
                .build();
        return Set.of(blockModelData);
    }
}
