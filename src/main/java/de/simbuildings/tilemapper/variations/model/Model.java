package de.simbuildings.tilemapper.variations.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import de.simbuildings.tilemapper.resourcepack.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Model {
    private final String parent;
    private final Map<String, String> textures;

    private Model(Builder builder) {
        this.parent = builder.parent;
        this.textures = builder.textures;
    }

    public static Model createBlock(Resource resource) {
        return new Model.Builder(ModelFile.BLOCK)
                .texture("all", resource)
                .build();
    }

    public static Set<Model> createSlab(Resource bottom, Resource top, Resource side) {
        TriFaceModel modelFactory = new TriFaceModel(bottom, top, side);
        return Set.of(
                modelFactory.createModel(ModelFile.SLAB),
                modelFactory.createModel(ModelFile.SLAB_TOP)
        );
    }

    public static Set<Model> createStairs(Resource bottom, Resource top, Resource side) {
        TriFaceModel modelFactory = new TriFaceModel(bottom, top, side);
        return Set.of(
                modelFactory.createModel(ModelFile.STAIRS),
                modelFactory.createModel(ModelFile.STAIRS_INNER),
                modelFactory.createModel(ModelFile.STAIRS_OUTER)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(parent, model.parent) && Objects.equals(textures, model.textures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, textures);
    }

    @Override
    public String toString() {
        return "Model{" +
               "parent='" + parent + '\'' +
               ", textures=" + textures +
               '}';
    }

    static class Builder {
        private final String parent;
        private final Map<String, String> textures = new HashMap<>();

        public Builder(ModelFile modelFile) {
            this.parent = modelFile.parent();
        }

        public Model.Builder texture(String textureVariable, Resource resource) {
            textures.put(textureVariable, resource.textureLocation());
            return this;
        }

        public Model build() {
            return new Model(this);
        }
    }
}

