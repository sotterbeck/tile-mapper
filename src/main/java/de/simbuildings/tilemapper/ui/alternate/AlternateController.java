package de.simbuildings.tilemapper.ui.alternate;

import dagger.Lazy;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class AlternateController implements Initializable {
    private final AlternateModel alternateModel;
    private final Lazy<Stage> variantPropertiesStage;

    @FXML
    private Parent root;
    @FXML
    private ListView<VariantModel> variantListView;
    @FXML
    private Button exportButton;
    @FXML
    private TextField materialTextField;
    @FXML
    private MenuItem faceMenuItem;
    @FXML
    private MenuItem weightMenuItem;

    @Inject
    public AlternateController(AlternateModel alternateModel,
                               @Named("variant_properties") Lazy<Stage> variantPropertiesStage) {
        this.alternateModel = alternateModel;
        this.variantPropertiesStage = variantPropertiesStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alternateModel.materialProperty().bind(materialTextField.textProperty());
        bindExportButton();
        bindListView();

        bindMenuItems();
    }

    private void bindMenuItems() {
        BooleanBinding variantIsNotSelected = alternateModel.selectedVariantProperty().isNull();
        faceMenuItem.disableProperty().bind(variantIsNotSelected);
        weightMenuItem.disableProperty().bind(variantIsNotSelected);
    }

    private void bindExportButton() {
        ListProperty<VariantModel> listProperty = new SimpleListProperty<>(alternateModel.variants());
        exportButton.disableProperty().bind(materialTextField.textProperty().isEmpty()
                .or(listProperty.emptyProperty()));
    }

    private void bindListView() {
        variantListView.setItems(alternateModel.variants());
        variantListView.setCellFactory(variantModelListView -> new VariantListCell());
        alternateModel.selectedVariantProperty().bind(variantListView.getSelectionModel().selectedItemProperty());
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
        if (!alternateModel.imagesPermitted(imagePaths)) {
            return;
        }
        alternateModel.addVariants(imagePaths);
    }

    @FXML
    private void handleShowVariantProperties(ActionEvent actionEvent) {
        variantPropertiesStage.get().show();
    }

    @FXML
    private void handleRemove(ActionEvent actionEvent) {
        if (variantListView.getSelectionModel().isEmpty()) {
            return;
        }
        alternateModel.removeVariant(variantListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleExport(ActionEvent actionEvent) {

    }

    private Window getWindow() {
        return root.getScene().getWindow();
    }
}
