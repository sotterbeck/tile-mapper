package de.simbuildings.tilemapper.ui.alternate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class AlternateController implements Initializable {
    private final AlternateModel alternateModel;
    public ListView<VariantModel> variantListView;

    @FXML
    private Parent root;
    @FXML
    private Button exportButton;
    @FXML
    private TextField materialTextField;

    @Inject
    public AlternateController(AlternateModel alternateModel) {
        this.alternateModel = alternateModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alternateModel.materialProperty().bind(materialTextField.textProperty());
        exportButton.disableProperty().bind(materialTextField.textProperty().isEmpty());

        variantListView.setItems(alternateModel.variants());
        variantListView.setCellFactory(variantModelListView -> new VariantListCell());
    }

    @FXML
    private void handleAdd(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG image file", "*.png"));

        List<File> files = fileChooser.showOpenMultipleDialog(getWindow());
        if (files == null) {
            return;
        }
        List<Path> imagePaths = files.stream()
                .map(File::toPath)
                .toList();
        alternateModel.addVariants(imagePaths);
    }

    @FXML
    private void handleShowVariantProperties(ActionEvent actionEvent) {

    }

    @FXML
    private void handleRemove(ActionEvent actionEvent) {

    }

    @FXML
    private void handleExport(ActionEvent actionEvent) {

    }

    private Window getWindow() {
        return root.getScene().getWindow();
    }
}
