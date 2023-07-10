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
    private final String directory;
    private final UnaryOperator<String> fileName;

    ModelFile(String parent, String directory, UnaryOperator<String> fileName) {
        this.parent = parent;
        this.directory = directory;
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

    /**
     * Returns the name of the parent directory where the model file is located within the model directory.
     *
     * @return the name of the parent directory of the model file.
     */
    public String parentDirectory() {
        return directory.isEmpty()
                ? ""
                : directory + "/";
    }

    /**
     * Returns the file name of the model for the specified variant.
     *
     * @param variant the variant for which the file name is requested.
     * @return the file name corresponding to the specified variant.
     */
    public String fileName(String variant) {
        return fileName.apply(variant);
    }
}
