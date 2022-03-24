module de.simbuildings.tilemapper {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.feather;
    requires thumbnailator;

    opens de.simbuildings.tilemapper.ui.controllers to javafx.fxml;
    exports de.simbuildings.tilemapper.ui.models;
    exports de.simbuildings.tilemapper;

    exports de.simbuildings.tilemapper.common;
    exports de.simbuildings.tilemapper.tile;
    exports de.simbuildings.tilemapper.image;
}