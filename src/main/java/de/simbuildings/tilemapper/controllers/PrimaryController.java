package de.simbuildings.tilemapper.controllers;

import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by SimBuildings on 15.10.21 at 18:34
 */
public class PrimaryController {

    @FXML
    private Button importButton, exportButton;
    @FXML
    private TextField blockTextField;
    @FXML
    private ComboBox<Integer> resolutionComboBox;

    private FileChooser fileChooser;
    private BufferedImage originalImage;

    public void handleImportButtonAction(ActionEvent event) throws IOException {
        File imageFile;

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PNG Image File", "*.png"));
        imageFile = fileChooser.showOpenDialog(importButton.getScene().getWindow());

        if (imageFile != null && ImageIO.read(imageFile) != null) {
            originalImage = ImageIO.read(imageFile);
            enableForm();
        } else {
            disableForm();
        }
    }


    public void handleExportButtonAction(ActionEvent event) {
    }


    // TODO form  (nested) class ? - Single Responsibility Priciple
    private void enableForm() {
        ImageResolution imageResolution = new ImageResolution(originalImage);
        if (imageResolution.isPowerOfTwo()) {
            resolutionComboBox.setDisable(false);
            blockTextField.setDisable(false);

            for (SquareImageResolution res :
                    imageResolution.getValuesPowerOfTwoUntilRes()) {
                resolutionComboBox.getItems().add(res.getHeight());
            }
        }
    }
    private void disableForm() {
        resolutionComboBox.setDisable(true);
        blockTextField.setDisable(true);

        resolutionComboBox.getItems().clear();
    }

}
