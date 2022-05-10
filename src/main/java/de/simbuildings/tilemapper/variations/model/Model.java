package de.simbuildings.tilemapper.variations.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import de.simbuildings.tilemapper.resourcepack.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Model {
    private final String parent;
    private final Map<String, String> textures;

    private Model(Builder builder) {
        this.parent = builder.parent;
        this.textures = builder.textures;
    }

    public static Model createBlock(Resource resource) {
        return new Model.Builder(ModelType.BLOCK)
                .texture("all", resource)
                .build();
    }

    public static Set<Model> createSlab(Resource bottom, Resource top, Resource side) {
        TriFaceModel modelFactory = new TriFaceModel(bottom, top, side);
        return Set.of(
                modelFactory.createModel(ModelType.SLAB),
                modelFactory.createModel(ModelType.SLAB_TOP)
        );
    }

    public static Set<Model> createStairs(Resource bottom, Resource top, Resource side) {
        TriFaceModel modelFactory = new TriFaceModel(bottom, top, side);
        return Set.of(
                modelFactory.createModel(ModelType.STAIRS),
                modelFactory.createModel(ModelType.STAIRS_INNER),
                modelFactory.createModel(ModelType.STAIRS_OUTER)
        );
    }

    @JsonGetter
    public String parent() {
        return parent;
    }

    @JsonGetter
    public Map<String, String> textures() {
        return textures;
    }

    static class Builder {
        private final String parent;
        private final Map<String, String> textures = new HashMap<>();

        public Builder(ModelType modelType) {
            this.parent = modelType.parent();
        }

        public Model.Builder texture(String textureVariable, Resource resourceLocation) {
            textures.put(textureVariable, resourceLocation.textureLocation());
            return this;
        }

        public Model build() {
            return new Model(this);
        }
    }

}

