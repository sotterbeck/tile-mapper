package de.simbuildings.tilemapper.variations;

import com.fasterxml.jackson.annotation.JsonGetter;
import de.simbuildings.tilemapper.resourcepack.Resource;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private final String parent;
    private final Map<String, String> textures;

    public Model(Builder builder) {
        this.parent = builder.parent;
        this.textures = builder.textures;
    }

    @JsonGetter
    public String parent() {
        return parent;
    }

    @JsonGetter
    public Map<String, String> textures() {
        return textures;
    }

    public static class Builder {
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

