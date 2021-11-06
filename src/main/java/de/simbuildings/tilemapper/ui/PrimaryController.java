package de.simbuildings.tilemapper.ui;

import de.simbuildings.tilemapper.image.SquareImageResolution;
import javafx.beans.binding.Bindings;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.Antialiasing;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by SimBuildings on 15.10.21 at 18:34
 */

// TODO: organize Controller: split functionality in preview and form
    // change preview grid color depending on image brightness
public class PrimaryController {
    private static final int SIDE_PADDING = 32 * 2;
    @FXML
    private BorderPane root;
    @FXML
    private Button importButton, exportButton;
    @FXML
    private TextField blockTextField;
    @FXML
    private ComboBox<Integer> resolutionComboBox;
    @FXML
    private Label importLabel;
    @FXML
    private VBox previewVbox;
    @FXML
    private StackPane imageStackPane;

    TileDataModel dataModel = new TileDataModel();

    @FXML
    public void initialize() {
        // enable export button when form is filled
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
        originalImageFile = fileChooser.showOpenDialog(root.getScene().getWindow());

        if (originalImageFile != null) {    // file is choosen
            try {
                dataModel.setOriginalImage(originalImageFile);
                enableForm();
            } catch (IllegalArgumentException e) {  // file format or size is wrong
                disableForm();
            }
        }
    }

    @FXML
    public void handleComboBoxAction(ActionEvent event) {
        if (resolutionComboBox.getValue() == null) {
            return;
        }
        // set resolution and update preview to change grid size
        dataModel.setTargetResolution(new SquareImageResolution(resolutionComboBox.getValue()));
        reloadPreview();
    }

    @FXML
    public void handleExportButtonAction(ActionEvent event) {
        dataModel.setBlock(blockTextField.getText());
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File exportDirectory = directoryChooser.showDialog(root.getScene().getWindow());

        if (exportDirectory != null) {
            dataModel.split();
            dataModel.export(exportDirectory);
        }
    }

    private void reloadPreview() {
        // remove old image and grid
        imageStackPane.getChildren().clear();

        GridPane grid = reloadGrid();
        ImageView previewImage = new ImageView();
        grid.setGridLinesVisible(true);
        imageStackPane.getChildren().addAll(previewImage, grid);
        // rescale image and show it
        try {
            BufferedImage image = Thumbnails.of(dataModel.getImageFile())
                    .size((int) (root.getWidth() - SIDE_PADDING), 400)
                    .scalingMode(ScalingMode.PROGRESSIVE_BILINEAR)
                    .antialiasing(Antialiasing.OFF).asBufferedImage();
            // show preview section
            previewVbox.setVisible(true);
            previewVbox.setManaged(true);

            previewImage.setImage(SwingFXUtils.toFXImage(image, null));
            // make grid same width as image
            grid.setMaxWidth(previewImage.getImage().getWidth());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private GridPane reloadGrid() {
        GridPane grid = new GridPane();
        // create columns
        for(int y = 0; y < dataModel.getGridWidth(); y++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100);
            column.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(column);
        }
        // create rows
        for(int i = 0; i < dataModel.getGridHeight(); i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100);
            row.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(row);
        }
        return grid;
    }

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
        // hide preview
        previewVbox.setVisible(false);
        previewVbox.setManaged(false);
    }
}
