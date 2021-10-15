package de.simbuildings.tilemapper.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by SimBuildings on 15.10.21 at 18:34
 */
public class PrimaryController {

    @FXML
    private Button importButton;

    private FileChooser fileChooser;
    private File imageFile;

    public void importFile() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PNG Image File", "*.png"));
        imageFile = fileChooser.showOpenDialog(importButton.getScene().getWindow());
        if (imageFile != null) {
            System.out.println("image file selected");
        }
    }
}
