package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.variations.blockstate.BlockState;

import java.util.Set;
import java.util.function.Function;

public enum BlockType {
    BLOCK(BlockState::createBlock, ""),
    STAIRS(BlockState::createStairs, "stairs"),
    SLAB(BlockState::createSlab, "slab");

    private final Function<Set<Variant.Builder>, BlockState> blockStateFactory;
    private final String blockStateSuffix;

    BlockType(Function<Set<Variant.Builder>, BlockState> blockStateFactory, String blockStateSuffix) {
        this.blockStateFactory = blockStateFactory;
        this.blockStateSuffix = blockStateSuffix;
    }

    public BlockState blockState(Set<Variant.Builder> variants) {
        return blockStateFactory.apply(variants);
    }

    public String blockStateSuffix() {
        return blockStateSuffix.isEmpty()
                ? ""
                : "_" + blockStateSuffix;
    }
}
