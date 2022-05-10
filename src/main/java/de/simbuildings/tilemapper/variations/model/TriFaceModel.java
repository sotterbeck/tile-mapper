package de.simbuildings.tilemapper.variations.model;

import de.simbuildings.tilemapper.resourcepack.Resource;

record TriFaceModel(Resource bottom, Resource top, Resource side) {

    public Model createModel(ModelType type) {
        return new Model.Builder(type)
                .texture("bottom", bottom)
                .texture("top", top)
                .texture("side", side)
                .build();
    }
}
