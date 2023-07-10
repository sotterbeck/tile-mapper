package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;

/**
 * Represents a variant in a <code>BlockState</code>.
 */
public interface BlockStateVariant extends Comparable<BlockStateVariant> {
    Resource resource();

    int weight();

    boolean uvLock();

    int rotationX();

    int rotationY();

    String model();

    String namespace();
}
