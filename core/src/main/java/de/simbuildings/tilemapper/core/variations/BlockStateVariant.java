package de.simbuildings.tilemapper.core.variations;

/**
 * Represents a variant in a <code>BlockState</code>.
 */
public interface BlockStateVariant extends Comparable<BlockStateVariant> {

    int weight();

    boolean uvLock();

    int rotationX();

    int rotationY();

    String model();
}
