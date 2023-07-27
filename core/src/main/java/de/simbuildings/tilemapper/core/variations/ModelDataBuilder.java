package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;


/**
 * Builder that allows the incremental creation of a ModelData object though adding multiple textures with
 * their corresponding texture variable name.
 */
public interface ModelDataBuilder {

    /**
     * Adds a texture and its corresponding vairable name to the <code>ModelData</code> being built.
     *
     * @param textureVariable The name of the texture variable.
     * @param resource        The resource of the texture.
     * @return The <code>ModelDataBuilder</code> to support method chaining.
     */
    ModelDataBuilder texture(String textureVariable, Resource resource);

    /**
     * Builds and returns the final ModelData object with the added textures.
     *
     * @return The ModelData object containing the added textures.
     */
    ModelData build();
}
