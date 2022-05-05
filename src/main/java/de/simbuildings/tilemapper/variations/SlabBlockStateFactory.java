package de.simbuildings.tilemapper.variations;

public class SlabBlockStateFactory implements BlockStateFactory {

    public BlockState createBlockState(String block, Variant.Builder variantBuilder) {
        variantBuilder.modelDirectory(block);
        return new BlockState.Builder()
                .namedVariant("type=bottom", new Variant.Builder(variantBuilder)
                        .modelDirectory(block + "/slab")
                        .modelSuffix("_slab")
                        .build())
                .namedVariant("type=double", new Variant.Builder(variantBuilder).build())
                .namedVariant("type=top", new Variant.Builder(variantBuilder)
                        .modelDirectory(block + "/slab")
                        .modelSuffix("_slab_top")
                        .build())
                .build();
    }
}
