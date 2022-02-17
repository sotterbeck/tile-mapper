package de.simbuildings.tilemapper.ui.controllers;

import de.simbuildings.tilemapper.image.SquareImageResolution;
import de.simbuildings.tilemapper.tile.ImageSplitter;
import de.simbuildings.tilemapper.tile.TilePropertiesWriter;
import de.simbuildings.tilemapper.ui.models.TileModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by SimBuildings on 12.02.22 at 21:32
 */
public class PrimaryController implements Initializable {
    private final TileModel tileModel = new TileModel();

    @FXML
    public Button importButton;
    @FXML
    public Label fileLabel;
    @FXML
    public ComboBox<Integer> resolutionComboBox;
    @FXML
    public TextField blockTextField;
    @FXML
    public Button exportButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingsDisableBinding();
        exportDisableBinding();

        tileModel.setFileLabelText("Select original image to split");
        fileLabel.textProperty().bind(tileModel.fileLabelTextProperty());

        resolutionComboBox.setItems(tileModel.validTargetResolutionsProperty());

        tileModel.blockNameProperty().bind(blockTextField.textProperty());
        tileModel.targetResolutionProperty().bind(resolutionComboBox.valueProperty());
    }

    private void exportDisableBinding() {
        exportButton.disableProperty().bind(
                tileModel.blockNameProperty().isEmpty()
                        .or(tileModel.targetResolutionProperty().isEqualTo(0))
        );
    }

    private void settingsDisableBinding() {
        blockTextField.disableProperty()
                .bind(tileModel.originalImageProperty().isNull());
        resolutionComboBox.disableProperty()
                .bind(tileModel.originalImageProperty().isNull());
    }

    @FXML
    private void handleImport(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG image file", "*.png"));

        File originalImage = fileChooser.showOpenDialog(importButton.getScene().getWindow());
        if (originalImage != null) {
            setOriginalImage(originalImage);
        }
    }

    private void setOriginalImage(File originalImage) {
        try {
            tileModel.setOriginalImage(originalImage);
        } catch (IOException | IllegalArgumentException e) {
            tileModel.setFileLabelText("Image has wrong resolution");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExport(ActionEvent actionEvent) {
        BufferedImage image = tileModel.getOriginalImage();
        SquareImageResolution targetResolution = new SquareImageResolution(tileModel.getTargetResolution());

        ImageSplitter imageSplitter = new ImageSplitter(image, targetResolution);
        TilePropertiesWriter propertiesWriter = new TilePropertiesWriter(imageSplitter.getTileGrid(), tileModel.getBlockName());
        imageSplitter.split();

        File outputDirectory = new DirectoryChooser().showDialog(importButton.getScene().getWindow());

        exportImagesAndProperties(imageSplitter, propertiesWriter, outputDirectory);
    }

    private void exportImagesAndProperties(ImageSplitter imageSplitter, TilePropertiesWriter propertiesWriter, File outputDirectory) {
        if (outputDirectory == null) {
            return;
        }
        // TODO: use paths instead of strings
        imageSplitter.save(outputDirectory.getPath() + "/");
        propertiesWriter.write(outputDirectory.getPath() + "/");
    }
}
