package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;

public record TriFaceModel(Resource modelResource, Resource bottom, Resource top, Resource side) {

    public ModelData createModel(ModelFile type) {
        return new JacksonModelData.Builder(type)
                .texture("bottom", bottom)
                .texture("top", top)
                .texture("side", side)
                .build();
    }
}
