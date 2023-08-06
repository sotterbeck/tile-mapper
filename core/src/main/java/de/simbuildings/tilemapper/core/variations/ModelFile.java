package de.simbuildings.tilemapper.core.variations;

import java.util.function.UnaryOperator;

/**
 * Provides access to the parent of a model and the location where the model json file should be stored.
 */
public enum ModelFile {
    BLOCK("cube_all",
            "",
            UnaryOperator.identity()),

    SLAB("slab",
            "slab",
            name -> name + "_slab"),
    SLAB_TOP("slab_top",
            "slab",
            name -> name + "_slab_top"),

    STAIRS("stairs",
            "stairs",
            name -> name + "_stairs"),
    STAIRS_INNER("inner_stairs",
            "stairs",
            name -> name + "_stairs_inner"),
    STAIRS_OUTER("outer_stairs",
            "stairs",
            name -> name + "_stairs_outer");

    private final String parent;
    private final String category;
    private final UnaryOperator<String> fileName;

    ModelFile(String parent, String category, UnaryOperator<String> fileName) {
        this.parent = parent;
        this.category = category;
        this.fileName = fileName;
    }

    /**
     * Returns the name of the parent model.
     *
     * @return the name of the parent model.
     */
    public String parent() {
        return "minecraft:block/" + parent;
    }

    public String category() {
        return category;
    }

    /**
     * Returns the name of the model file.
     *
     * @param variant the variant for which the model name is requested.
     * @return the model name corresponding to the specified variant.
     */
    public String name(String variant) {
        return fileName.apply(variant);
    }
}
