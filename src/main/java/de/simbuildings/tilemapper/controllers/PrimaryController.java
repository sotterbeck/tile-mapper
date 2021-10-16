package de.simbuildings.tilemapper.controllers;

import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;
import de.simbuildings.tilemapper.tile.ImageSpliter;
import javafx.beans.binding.Bindings;
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

    private File originalImageFile;
    private BufferedImage originalImage;

    @FXML
    public void initialize() {
        exportButton.disableProperty().bind(
                Bindings.isNull(resolutionComboBox.valueProperty()).or(Bindings.isEmpty(blockTextField.textProperty()))
        );
    }

    public void handleImportButtonAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PNG Image File", "*.png"));
        originalImageFile = fileChooser.showOpenDialog(importButton.getScene().getWindow());

        if (originalImageFile != null && ImageIO.read(originalImageFile) != null) {
            originalImage = ImageIO.read(originalImageFile);
            enableForm();
        } else {
            disableForm();
        }
    }


    public void handleExportButtonAction(ActionEvent event) {
        SquareImageResolution targetResolution = new SquareImageResolution(resolutionComboBox.getValue());
        String destDir = originalImageFile.getParentFile().getAbsolutePath() + "/";

        ImageSpliter imageSpliter = new ImageSpliter(originalImage, targetResolution);

        imageSpliter.split();
        imageSpliter.exportTiles(destDir);
    }


    // TODO form (nested) class ? - Single Responsibility Priciple
    private void enableForm() {
        ImageResolution imageResolution = new ImageResolution(originalImage);
        if (imageResolution.isPowerOfTwo()) {
            resolutionComboBox.setDisable(false);
            blockTextField.setDisable(false);

            for (SquareImageResolution res :
                    imageResolution.getValuesPowerOfTwoUntilRes()) {
                resolutionComboBox.getItems().add(res.getHeight());
            }
        } else {
            disableForm();
        }
    }
    private void disableForm() {
        resolutionComboBox.setDisable(true);
        blockTextField.setDisable(true);
        resolutionComboBox.getItems().clear();
    }

}
