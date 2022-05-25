package de.simbuildings.tilemapper.variations.model;

import de.simbuildings.tilemapper.resourcepack.Resource;

record TriFaceModel(Resource modelResource, Resource bottom, Resource top, Resource side) {

    public Model createModel(ModelFile type) {
        return new Model.Builder(type, modelResource)
                .texture("bottom", bottom)
                .texture("top", top)
                .texture("side", side)
                .build();
    }
}
