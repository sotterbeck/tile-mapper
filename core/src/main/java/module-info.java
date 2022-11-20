module de.simbuildings.tilemapper.core {
    requires com.fasterxml.jackson.databind;
    requires net.harawata.appdirs;
    requires java.desktop;

    exports de.simbuildings.tilemapper.core.common;
    exports de.simbuildings.tilemapper.core.ctm;
    exports de.simbuildings.tilemapper.core.image;
    exports de.simbuildings.tilemapper.core.resourcepack;
    exports de.simbuildings.tilemapper.core.tile;
    exports de.simbuildings.tilemapper.core.util;
    exports de.simbuildings.tilemapper.core.variations;

    opens de.simbuildings.tilemapper.core.resourcepack to com.fasterxml.jackson.databind;
}