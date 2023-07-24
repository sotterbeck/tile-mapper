package de.simbuildings.tilemapper.core.variations;

import com.fasterxml.jackson.annotation.JsonGetter;
import de.simbuildings.tilemapper.core.resourcepack.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a Minecraft model. Intended to be serialized to json.
 */
public class JacksonModel implements Model {
    private final Resource modelResource;
    private final ModelFile modelFile;
    private final Map<String, String> textures;

    private JacksonModel(Builder builder) {
        this.modelResource = builder.targetResource;
        this.modelFile = builder.modelFile;
        this.textures = builder.textures;
    }

    @Override
    @JsonGetter
    public String parent() {
        return modelFile.parent();
    }

    @Override
    @JsonGetter
    public Map<String, String> textures() {
        return textures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JacksonModel model = (JacksonModel) o;
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

        public JacksonModel.Builder texture(String textureVariable, Resource resource) {
            textures.put(textureVariable, resource.textureLocation());
            return this;
        }

        public Model build() {
            return new JacksonModel(this);
        }
    }
}