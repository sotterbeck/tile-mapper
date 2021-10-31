package de.simbuildings.tilemapper.ui;

import de.simbuildings.tilemapper.image.ImageResolution;
import de.simbuildings.tilemapper.image.SquareImageResolution;
import de.simbuildings.tilemapper.tile.ImageSpliter;
import de.simbuildings.tilemapper.tile.TilePropertiesWriter;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    @FXML
    private Label importLabel;

    private File originalImageFile;
    private BufferedImage originalImage;

    @FXML
    public void initialize() {
        exportButton.disableProperty().bind(
                Bindings.isNull(resolutionComboBox.valueProperty()).or(Bindings.isEmpty(blockTextField.textProperty()))
        );
    }

    @FXML
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

    @FXML
    public void handleExportButtonAction(ActionEvent event) {
        SquareImageResolution targetResolution = new SquareImageResolution(resolutionComboBox.getValue());
        String destDir = originalImageFile.getParentFile().getAbsolutePath() + "/";

        ImageSpliter imageSpliter = new ImageSpliter(originalImage, targetResolution);
        TilePropertiesWriter properties = new TilePropertiesWriter(imageSpliter.getTileGrid(), blockTextField.getText());

        imageSpliter.split();
        imageSpliter.save(destDir);
        properties.write(destDir);
    }

    // TODO form (nested) class ? - Single Responsibility Priciple
    private void enableForm() {
        ImageResolution imageResolution = new ImageResolution(originalImage);
        if (imageResolution.isPowerOfTwo()) {
            resolutionComboBox.setDisable(false);
            blockTextField.setDisable(false);
            importLabel.setText(originalImageFile.getName());


            resolutionComboBox.getItems().clear();
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
        importLabel.setText("Select valid image");
        resolutionComboBox.getItems().clear();
    }

}
