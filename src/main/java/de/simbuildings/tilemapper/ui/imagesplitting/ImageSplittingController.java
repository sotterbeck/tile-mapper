package de.simbuildings.tilemapper.ui.imagesplitting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by SimBuildings on 12.02.22 at 21:32
 */
public class ImageSplittingController implements Initializable {
    private final TileModel tileModel;

    private final DragAndDropModel dragAndDropModel = new DragAndDropModel();

    @FXML
    public Button importButton;
    @FXML
    public Label fileLabel;
    @FXML
    public ComboBox<Integer> resolutionComboBox;
    @FXML
    public TextField blockTextField;
    @FXML
    public TilePreview tilePreview;
    @FXML
    public Button exportButton;
    @FXML
    public DragAndDropOverlay dragAndDropOverlay;
    @FXML
    public Parent root;

    @Inject
    public ImageSplittingController(TileModel tileModel) {
        this.tileModel = tileModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindSettingsDisableBinding();
        bindExportDisableBinding();

        tilePreview.setTileModel(tileModel);
        dragAndDropOverlay.setDragAndDropModel(dragAndDropModel);

        tileModel.setFileLabelText("Select original image to split");
        fileLabel.textProperty().bind(tileModel.fileLabelTextProperty());

        resolutionComboBox.setItems(tileModel.validTargetResolutionsProperty());

        tileModel.blockNameProperty().bind(blockTextField.textProperty());
        tileModel.targetResolutionProperty().bind(resolutionComboBox.valueProperty());

        setUpDragAndDrop();
    }

    private void bindExportDisableBinding() {
        exportButton.disableProperty().bind(
                tileModel.blockNameProperty().isEmpty()
                        .or(tileModel.targetResolutionProperty().isEqualTo(0))
        );
    }

    private void bindSettingsDisableBinding() {
        blockTextField.disableProperty()
                .bind(tileModel.originalImageProperty().isNull());
        resolutionComboBox.disableProperty()
                .bind(tileModel.originalImageProperty().isNull());
    }

    private void setUpDragAndDrop() {
        root.setOnDragOver(event -> event.acceptTransferModes(TransferMode.COPY_OR_MOVE));

        root.setOnDragEntered(event -> dragAndDropModel.setDraggingProperty(true));
        root.setOnDragExited(event -> dragAndDropModel.setDraggingProperty(false));
        root.setOnDragDropped(event -> {
            dragAndDropModel.setDraggingProperty(false);
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                setOriginalImage(dragboard.getFiles().get(0));
            }
        });
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExport(ActionEvent actionEvent) {
        File outputDirectory = new DirectoryChooser().showDialog(importButton.getScene().getWindow());
        if (outputDirectory == null) {
            return;
        }

        if (tileModel.outputExists(outputDirectory)) {
            System.out.println("conflict");
            return;
        }
        exportCtmBlock(outputDirectory);
    }

    private void exportCtmBlock(File outputDirectory) {
        try {
            tileModel.export(outputDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSettingsButton(ActionEvent actionEvent) {

    }
}