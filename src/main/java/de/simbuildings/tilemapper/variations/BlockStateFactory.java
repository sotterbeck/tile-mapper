package de.simbuildings.tilemapper.variations;

public interface BlockStateFactory {
    BlockState createBlockState(String block, Variant.Builder variantBuilder);
}
