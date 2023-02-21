module de.simbuildings.tilemapper.core {
    requires com.fasterxml.jackson.databind;
    requires net.harawata.appdirs;
    requires java.desktop;

    exports de.simbuildings.tilemapper.core.common;
    exports de.simbuildings.tilemapper.core.ctm;
    exports de.simbuildings.tilemapper.core.image;
    exports de.simbuildings.tilemapper.core.resourcepack;
    exports de.simbuildings.tilemapper.core.tile;
    exports de.simbuildings.tilemapper.core.variations;
    exports de.simbuildings.tilemapper.core.variations.model;

    opens de.simbuildings.tilemapper.core.resourcepack to com.fasterxml.jackson.databind;
}