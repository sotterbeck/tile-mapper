package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.DefaultResourcepackPathProvider;
import de.simbuildings.tilemapper.core.resourcepack.Resource;
import de.simbuildings.tilemapper.core.resourcepack.ResourcepackPathProvider;

public record TriFaceModel(
        Resource modelResource,
        Resource bottom,
        Resource top,
        Resource side
) {

    public ModelData createModel(ModelFile type, ResourcepackPathProvider pathProvider) {
        return new JacksonModelData.Builder(type, pathProvider)
                .texture("bottom", bottom)
                .texture("top", top)
                .texture("side", side)
                .build();
    }
}
