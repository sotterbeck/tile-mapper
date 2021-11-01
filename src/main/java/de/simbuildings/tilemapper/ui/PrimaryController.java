package de.simbuildings.tilemapper.ui;

import de.simbuildings.tilemapper.image.SquareImageResolution;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

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
    @FXML
    private Label importLabel;
    @FXML
    public GridPane imageGrid;

    // only ImageSplitter after rewrite
    TileDataModel dataModel = new TileDataModel();

    /*
    TODO:
     REWRITE!
     - use image splitter with setters and set imageGrid width and height
     - imageGrid with css background image
     -> use MVC pattern to achive this?

     - tiles as ImageView inside imageGrid
     Last option: use swing component
     */

    @FXML
    public void initialize() {
        exportButton.disableProperty().bind(
                Bindings.isNull(resolutionComboBox.valueProperty()).or(Bindings.isEmpty(blockTextField.textProperty()))
        );
        // TODO: set placeholders and save test in constants e.g. RESOLUTION_PLACEHOLDER
    }

    @FXML
    public void handleImportButtonAction(ActionEvent event) throws IOException {
        File originalImageFile;

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PNG Image File", "*.png"));
        originalImageFile = fileChooser.showOpenDialog(importButton.getScene().getWindow());

        if (originalImageFile != null) { // null check is necessary
            try {
                dataModel.setOriginalImage(originalImageFile);
                enableForm();
            } catch (IllegalArgumentException e) {
                disableForm();
            }
        }
    }

    @FXML
    public void handleComboBoxAction(ActionEvent event) {
        if (resolutionComboBox.getValue() == null) {
            return;
        }
        System.out.println(resolutionComboBox.getValue());
        dataModel.setTargetResolution(new SquareImageResolution(resolutionComboBox.getValue()));
    }

    @FXML
    public void handleExportButtonAction(ActionEvent event) {
        dataModel.setBlock(blockTextField.getText());
        dataModel.split();
        dataModel.export();

    }

    // TODO form (nested) class ? - Single Responsibility Priciple
    private void enableForm() {
        // enable inputs if image is set and valid
        resolutionComboBox.setDisable(false);
        blockTextField.setDisable(false);
        importLabel.setText(dataModel.getFileName());

        // reset previous ComboBox images and add valid target resolutions
        resolutionComboBox.getItems().clear();
        for (SquareImageResolution res :
                dataModel.getOriginalResolution().getValuesPowerOfTwoUntilRes()) {
            resolutionComboBox.getItems().add(res.getHeight());
        }
}

    private void disableForm() {
        // disable all inputs
        resolutionComboBox.setDisable(true);
        blockTextField.setDisable(true);
        importLabel.setText("Select valid image");
    }
}
