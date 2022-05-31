module de.simbuildings.tilemapper {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires net.harawata.appdirs;

    requires dagger;
    requires javax.inject;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.feather;
    requires thumbnailator;

    opens de.simbuildings.tilemapper to javafx.graphics;
    opens de.simbuildings.tilemapper.resourcepack to com.fasterxml.jackson.databind;
    opens de.simbuildings.tilemapper.common to com.fasterxml.jackson.databind;
    opens de.simbuildings.tilemapper.ui.imagesplitting to javafx.fxml;
    opens de.simbuildings.tilemapper.ui.resourcepack to javafx.fxml;
    opens de.simbuildings.tilemapper.ui.common to javafx.fxml;
    opens de.simbuildings.tilemapper.variations to com.fasterxml.jackson.databind;
    opens de.simbuildings.tilemapper.variations.blockstate to com.fasterxml.jackson.databind;
    opens de.simbuildings.tilemapper.variations.model to com.fasterxml.jackson.databind;

    exports de.simbuildings.tilemapper.common;
    exports de.simbuildings.tilemapper.tile;
    exports de.simbuildings.tilemapper.image;
    exports de.simbuildings.tilemapper.variations;
    exports de.simbuildings.tilemapper.variations.model;
    exports de.simbuildings.tilemapper.variations.blockstate;
    exports de.simbuildings.tilemapper.resourcepack;
    exports de.simbuildings.tilemapper.ui.imagesplitting;
    exports de.simbuildings.tilemapper.ui.resourcepack;
    exports de.simbuildings.tilemapper.ui.common;
    exports de.simbuildings.tilemapper.injection;
    exports de.simbuildings.tilemapper.injection.jfx;
    exports de.simbuildings.tilemapper.ctm;
    exports de.simbuildings.tilemapper.util;
    opens de.simbuildings.tilemapper.util to com.fasterxml.jackson.databind;
}