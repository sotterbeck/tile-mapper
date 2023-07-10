package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;

record TriFaceModel(Resource modelResource, Resource bottom, Resource top, Resource side) {

    Model createModel(ModelFile type) {
        return new JacksonModel.Builder(type, modelResource)
                .texture("bottom", bottom)
                .texture("top", top)
                .texture("side", side)
                .build();
    }
}
