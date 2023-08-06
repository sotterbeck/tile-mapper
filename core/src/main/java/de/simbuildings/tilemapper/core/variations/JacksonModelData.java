package de.simbuildings.tilemapper.core.variations;

import com.fasterxml.jackson.annotation.JsonGetter;
import de.simbuildings.tilemapper.core.resourcepack.Resource;
import de.simbuildings.tilemapper.core.resourcepack.ResourcepackPathProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a Minecraft model. Intended to be serialized to json.
 */
public class JacksonModelData implements ModelData {
    private final String parent;
    private final Map<String, String> textures;

    private JacksonModelData(Builder builder) {
        this.parent = builder.modelFile.parent();
        this.textures = builder.textures;
    }

    @Override
    @JsonGetter
    public String parent() {
        return parent;
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
        JacksonModelData that = (JacksonModelData) o;
        return Objects.equals(parent, that.parent) && Objects.equals(textures, that.textures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, textures);
    }

    @Override
    public String toString() {
        return "JacksonModelData{" +
                "parent='" + parent + '\'' +
                ", textures=" + textures +
                '}';
    }

    /**
     * Builder that allows the incremental creation of a <code>ModelData</code> object though adding multiple
     * textures with their corresponding texture variable name.
     * <p>
     * The resulting <code>ModelData</code> object supports serialization to json using Jackson.
     */
    public static class Builder implements ModelDataBuilder {
        private final ModelFile modelFile;
        private final Map<String, String> textures = new HashMap<>();
        private final ResourcepackPathProvider pathProvider;

        public Builder(ModelFile modelFile, ResourcepackPathProvider pathProvider) {
            this.modelFile = modelFile;
            this.pathProvider = pathProvider;
        }

        @Override
        public ModelDataBuilder texture(String textureVariable, Resource resource) {
            textures.put(textureVariable, pathProvider.textureLocation());
            return this;
        }

        @Override
        public ModelData build() {
            return new JacksonModelData(this);
        }
    }
}