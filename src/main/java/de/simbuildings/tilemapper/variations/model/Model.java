package de.simbuildings.tilemapper.variations.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import de.simbuildings.tilemapper.resourcepack.Resource;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Model {
    private final Resource modelResource;
    private final ModelFile modelFile;
    private final Map<String, String> textures;

    private Model(Builder builder) {
        this.modelResource = builder.targetResource;
        this.modelFile = builder.modelFile;
        this.textures = builder.textures;
    }

    public static Model createBlock(Resource modelResource, Resource resource) {
        return new Model.Builder(ModelFile.BLOCK, modelResource)
                .texture("all", resource)
                .build();
    }

    public static Set<Model> createSlab(Resource modelResource, Resource bottom, Resource top, Resource side) { // TODO: resource for multiple textures with builder
        TriFaceModel modelFactory = new TriFaceModel(modelResource, bottom, top, side);
        return Set.of(
                modelFactory.createModel(ModelFile.SLAB),
                modelFactory.createModel(ModelFile.SLAB_TOP)
        );
    }

    public static Set<Model> createStairs(Resource modelResource, Resource bottom, Resource top, Resource side) {
        TriFaceModel modelFactory = new TriFaceModel(modelResource, bottom, top, side);
        return Set.of(
                modelFactory.createModel(ModelFile.STAIRS),
                modelFactory.createModel(ModelFile.STAIRS_INNER),
                modelFactory.createModel(ModelFile.STAIRS_OUTER)
        );
    }

    public Path file() {
        return modelResource.modelFile(modelFile);
    }

    @JsonGetter
    public String parent() {
        return modelFile.parent();
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
        return Objects.equals(modelFile, model.modelFile) && Objects.equals(textures, model.textures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelFile, textures);
    }

    @Override
    public String toString() {
        return "Model{" +
               "parent='" + modelFile + '\'' +
               ", textures=" + textures +
               '}';
    }

    static class Builder {
        private final ModelFile modelFile;
        private final Resource targetResource;
        private final Map<String, String> textures = new HashMap<>();

        public Builder(ModelFile modelFile, Resource targetResource) {
            this.modelFile = modelFile;
            this.targetResource = targetResource;
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