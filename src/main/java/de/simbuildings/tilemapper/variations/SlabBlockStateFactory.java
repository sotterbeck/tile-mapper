package de.simbuildings.tilemapper.variations;

public class SlabBlockStateFactory implements BlockStateFactory {

    @Override
    public BlockState createBlockState(String block, Variant variant) {
        return new BlockState.Builder()
                .namedVariant("type=bottom",
                        variant.withModel("%s/slab/%s_slab".formatted(block, variant.model())))
                .namedVariant("type=double",
                        variant.withModel("%s/%s".formatted(block, variant.model())))
                .namedVariant("type=top", variant.withModel("%s/slab/%s_slab".formatted(block, variant.model())))
                .build();
    }
}
