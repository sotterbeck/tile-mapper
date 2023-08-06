package de.simbuildings.tilemapper.core.variations;

import java.util.Set;
import java.util.stream.Stream;

/**
 * A builder for creating a BlockStateData object, allowing the incremental addition of multiple variants.
 */
public interface BlockStateDataBuilder {

    /**
     * Adds a set of block state variants to the specified variant name in the <code>BlockStateData</code> being built.
     *
     * @param variantName the name of the variant to which the block state variants are added.
     * @param variants    the set of <code>BlockStateVariant</code> objects representing the block states.
     * @return the <code>BlockStateDataBuilder</code> instance to support method chaining.
     */
    BlockStateDataBuilder variants(String variantName, Set<BlockStateVariant> variants);

    /**
     * Adds a stream of <code>BlockStateVariantBuilder</code> objects to the specified variant name in the
     * <code>BlockStateData</code> being built.
     *
     * @param variantName     the name of the variant to which the block state variants are added.
     * @param variantBuilders the Stream of <code>BlockStateVariantBuilder</code> objects representing the block state
     *                        variants.
     * @return the <code>BlockStateDataBuilder</code> instance to support method chaining.
     */
    BlockStateDataBuilder variantStream(String variantName, Stream<BlockStateVariantBuilder> variantBuilders);

    /**
     * Builds and returns the final <code>BlockStateData</code> object with the added variants.
     *
     * @return the <code>BlockStateData</code> object containing the added variants.
     */
    BlockStateData build();
}
