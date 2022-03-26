module de.simbuildings.tilemapper {
    requires java.prefs;

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires net.harawata.appdirs;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.feather;
    requires thumbnailator;

    opens de.simbuildings.tilemapper.ui.controllers to javafx.fxml;
    opens de.simbuildings.tilemapper.resourcepack to com.fasterxml.jackson.databind;

    exports de.simbuildings.tilemapper.ui.models;
    exports de.simbuildings.tilemapper;

    exports de.simbuildings.tilemapper.common;
    exports de.simbuildings.tilemapper.tile;
    exports de.simbuildings.tilemapper.image;
    exports de.simbuildings.tilemapper.resourcepack;
}