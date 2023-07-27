package de.simbuildings.tilemapper.core.variations;

import com.fasterxml.jackson.annotation.JsonGetter;
import de.simbuildings.tilemapper.core.resourcepack.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a Minecraft model. Intended to be serialized to json.
 */
public class JacksonModelData implements ModelData {
    private final ModelFile modelFile;
    private final Map<String, String> textures;

    private JacksonModelData(Builder builder) {
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
        JacksonModelData model = (JacksonModelData) o;
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

    /**
     * Builder that allows the incremental creation of a <code>ModelData</code> object though adding multiple
     * textures with their corresponding texture variable name.
     */
    public static class Builder implements ModelDataBuilder {
        private final ModelFile modelFile;
        private final Map<String, String> textures = new HashMap<>();

        public Builder(ModelFile modelFile) {
            this.modelFile = modelFile;
        }

        @Override
        public ModelDataBuilder texture(String textureVariable, Resource resource) {
            textures.put(textureVariable, resource.textureLocation());
            return this;
        }

        @Override
        public ModelData build() {
            return new JacksonModelData(this);
        }
    }
}