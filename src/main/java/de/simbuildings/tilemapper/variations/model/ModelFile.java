package de.simbuildings.tilemapper.variations.model;

import java.util.function.UnaryOperator;

public enum ModelFile {
    BLOCK("cube_all", "", UnaryOperator.identity()),

    SLAB("slab", "slab", name -> name + "_slab"),
    SLAB_TOP("slab_top", "slab", name -> name + "_slab_top"),

    STAIRS("stairs", "stairs", name -> name + "_stairs"),
    STAIRS_INNER("inner_stairs", "stairs", name -> name + "_stairs_inner"),
    STAIRS_OUTER("outer_stairs", "stairs", name -> name + "_stairs_outer");

    private final String parent;
    private final String directory;
    private final UnaryOperator<String> fileName;

    ModelFile(String parent, String directory, UnaryOperator<String> fileName) {
        this.parent = parent;
        this.directory = directory;
        this.fileName = fileName;
    }

    public String parent() {
        return "minecraft:block/" + parent;
    }

    public String directory() {
        if (directory.equals("")) {
            return "";
        }
        return directory + "/";
    }

    public String fileName(String variant) {
        return fileName.apply(variant);
    }
}
