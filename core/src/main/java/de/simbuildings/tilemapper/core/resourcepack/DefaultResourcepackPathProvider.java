package de.simbuildings.tilemapper.core.resourcepack;

import de.simbuildings.tilemapper.core.variations.ModelFile;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A <code>ResourcepackPathProvider</code> that organizes model files into directories named after
 * the category of model file.
 * <p>
 * For example all model files related to stairs are in the parent directory named "stairs".
 */
public class DefaultResourcepackPathProvider implements ResourcepackPathProvider {

    private static final String ASSET_ROOT = "assets";
    private final Resource resource;

    public DefaultResourcepackPathProvider(Resource resource) {
        this.resource = resource;
    }

    @Override
    public String textureLocation() {
        return "%s:block/%s/%s".formatted(resource.namespace(),
                resource.material(),
                resource.variant());
    }

    @Override
    public Path textureDirectory() {
        return Paths.get(ASSET_ROOT, resource.namespace(), "textures", "block", resource.material());
    }

    @Override
    public String modelLocation(ModelFile modelFile) {
        return "%s:block/%s/%s%s".formatted(resource.namespace(),
                resource.material(),
                modelParentDirectory(modelFile),
                modelFile.name(resource.variant()));
    }

    @Override
    public Path modelPath(ModelFile modelFile) {
        return Paths.get(ASSET_ROOT, resource.namespace(), "models", "block", resource.material(), modelParentDirectory(modelFile), modelFile.name(resource.variant()) + ".json");
    }

    private String modelParentDirectory(ModelFile modelFile) {
        String category = modelFile.category();
        return category.isEmpty()
                ? ""
                : category + "/";
    }
}