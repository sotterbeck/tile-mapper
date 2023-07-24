package de.simbuildings.tilemapper.core.variations;

import java.util.Map;
import java.util.Set;

public interface BlockState {
    Map<String, Set<BlockStateVariant>> variants();
}
