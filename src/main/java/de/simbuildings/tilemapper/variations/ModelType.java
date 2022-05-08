package de.simbuildings.tilemapper.variations;

import java.util.function.UnaryOperator;

public enum ModelType {
    BLOCK("cube_all", "", UnaryOperator.identity()),
    SLAB("slab", "slab", name -> name + "_slab"),
    SLAB_TOP("slab_top", "slab", name -> name + "_slab_top");

    private final String parent;
    private final String directory;
    private final UnaryOperator<String> fileName;

    ModelType(String parent, String directory, UnaryOperator<String> fileName) {
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
