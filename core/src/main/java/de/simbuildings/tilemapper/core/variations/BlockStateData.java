package de.simbuildings.tilemapper.core.variations;

import java.util.Map;
import java.util.Set;

/**
 * Represents a Minecraft block state file, which consists of multiple variants.
 */
public interface BlockStateData {

    /**
     * Returns a map of variant names to their corresponding sets of block states.
     *
     * @return the map containing variant names as keys and sets of block state variants as values.
     */
    Map<String, Set<BlockStateVariant>> variants();
}
