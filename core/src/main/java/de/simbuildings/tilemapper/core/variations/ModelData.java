package de.simbuildings.tilemapper.core.variations;

import java.util.Map;

/**
 * Represents a Minecraft model file. It consists of a parent model and textures that correspond to their
 * assigned texture variable.
 */
public interface ModelData {

    /**
     * @return the name of the parent model.
     */
    String parent();

    /**
     * Returns a map of texture variables to their corresponding texture names.
     *
     * @return the map containing texture variables as keys and their assigned texture names as values.
     */
    Map<String, String> textures();
}
