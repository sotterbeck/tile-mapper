module de.simbuildings.tilemapper.gui {
    requires javax.inject;
    requires dagger;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires net.coobird.thumbnailator;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.feather;
    requires com.fasterxml.jackson.databind;
    requires de.simbuildings.tilemapper.core;

    opens de.simbuildings.tilemapper.gui to javafx.graphics;
    opens de.simbuildings.tilemapper.gui.resourcepack to com.fasterxml.jackson.databind, javafx.fxml;
    opens de.simbuildings.tilemapper.gui.imagesplitting to javafx.fxml;
    opens de.simbuildings.tilemapper.gui.alternate to com.fasterxml.jackson.databind, javafx.base, javafx.fxml;
    opens de.simbuildings.tilemapper.gui.common to com.fasterxml.jackson.databind, javafx.fxml, javafx.graphics;
}