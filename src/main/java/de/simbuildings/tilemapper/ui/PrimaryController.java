package de.simbuildings.tilemapper.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

/**
 * Created by SimBuildings on 15.10.21 at 18:34
 */

// change preview grid color depending on image brightness
public class PrimaryController {
    static TileDataModel dataModel = new TileDataModel();

    @FXML
    public SettingsForm settingsForm;
    @FXML
    public TilePreview tilePreview;
    @FXML
    private BorderPane root;
    @FXML
    private Button importButton;
    @FXML
    private Button exportButton;
    @FXML
    private Label importLabel;
    @FXML
    private VBox previewVbox;

    @FXML
    public void initialize() {
        // enable export button when form is filled
        exportButton.disableProperty().bind(settingsForm.filledBinding());
        settingsForm.setPreview(tilePreview);
    }

    @FXML
    public void handleImportButtonAction(ActionEvent event) throws IOException {
        File originalImageFile;

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PNG Image File", "*.png"));
        originalImageFile = fileChooser.showOpenDialog(root.getScene().getWindow());

        if (originalImageFile != null) {    // file is choosen
            try {
                dataModel.setOriginalImage(originalImageFile);
                importLabel.setText(dataModel.getFileName());
                settingsForm.enable();
            } catch (IllegalArgumentException e) {  // file format or size is wrong
                settingsForm.disable();
                importLabel.setText("Select valid image");
            }
        }
    }

    @FXML
    public void handleExportButtonAction(ActionEvent event) {
        dataModel.setBlock(settingsForm.getBlock());
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File exportDirectory = directoryChooser.showDialog(root.getScene().getWindow());

        if (exportDirectory != null) {
            dataModel.split();
            dataModel.export(exportDirectory);
        }
    }
}
