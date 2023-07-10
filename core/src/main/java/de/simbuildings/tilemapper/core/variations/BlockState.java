package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public interface BlockState {
    Map<String, Set<BlockStateVariant>> variants();

    Path resourcepackLocation(Resource resource);
}
