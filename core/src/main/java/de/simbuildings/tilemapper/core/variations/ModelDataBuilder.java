package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.resourcepack.Resource;


/**
 * Builder that allows the incremental creation of a <code>ModelData</code> object though adding multiple textures with
 * their corresponding texture variable name.
 */
public interface ModelDataBuilder {

    /**
     * Adds a texture and its corresponding variable name to the <code>ModelData</code> being built.
     *
     * @param textureVariable the name of the texture variable.
     * @param resource        the resource of the texture.
     * @return the <code>ModelDataBuilder</code> to support method chaining.
     */
    ModelDataBuilder texture(String textureVariable, Resource resource);

    /**
     * Builds and returns the final ModelData object with the added textures.
     *
     * @return the <code>ModelData</code> object containing the added textures.
     */
    ModelData build();
}
