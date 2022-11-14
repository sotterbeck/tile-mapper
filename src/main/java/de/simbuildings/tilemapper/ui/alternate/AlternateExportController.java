package de.simbuildings.tilemapper.ui.alternate;

import dagger.Lazy;
import de.simbuildings.tilemapper.resourcepack.Resourcepack;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackListCell;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AlternateExportController implements Initializable {
    private final AlternateModel alternateModel;
    private final ResourcepackModel resourcepackModel;
    private final Lazy<Stage> resourcepackStage;

    @FXML
    private Parent root;
    @FXML
    private ComboBox<VariantModel> defaultTextureComboBox;
    @FXML
    private ComboBox<Resourcepack> resourcepackComboBox;
    @FXML
    private CheckBox blockCheckBox;
    @FXML
    private CheckBox slabsCheckBox;
    @FXML
    private CheckBox stairsCheckBox;
    @FXML
    private Button exportButton;

    @Inject
    public AlternateExportController(AlternateModel alternateModel,
                                     ResourcepackModel resourcepackModel,
                                     @Named("resourcepack") Lazy<Stage> resourcepackStage) {
        this.alternateModel = alternateModel;
        this.resourcepackModel = resourcepackModel;
        this.resourcepackStage = resourcepackStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindResourcepackComboBox();
        bindDefaultTextureComboBox();
        bindExportButton();
    }

    private void bindExportButton() {
        BooleanBinding noBlockTypeSelected = blockCheckBox.selectedProperty().not()
                .and(slabsCheckBox.selectedProperty().not()
                        .and(stairsCheckBox.selectedProperty().not()));

        exportButton.disableProperty().bind(
                defaultTextureComboBox.getSelectionModel().selectedItemProperty().isNull()
                        .or(resourcepackComboBox.getSelectionModel().selectedItemProperty().isNull())
                        .or(noBlockTypeSelected)
                        .or(resourcepackComboBox.getSelectionModel().selectedItemProperty().isNull())
        );
    }

    private void bindDefaultTextureComboBox() {
        defaultTextureComboBox.setItems(alternateModel.variants());
        defaultTextureComboBox.setCellFactory(param -> new SmallVariantListCell());
        defaultTextureComboBox.setButtonCell(new SmallVariantListCell());
        defaultTextureComboBox.getSelectionModel().selectFirst();
    }

    private void bindResourcepackComboBox() {
        resourcepackComboBox.setItems(resourcepackModel.resourcepacksProperty());
        resourcepackComboBox.setCellFactory(param -> new ResourcepackListCell());
        resourcepackComboBox.setButtonCell(new ResourcepackListCell());
    }

    @FXML
    private void handleAddResourcepack(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(root.getScene().getWindow());
    }

    @FXML
    private void handleManageResourcepacks(ActionEvent actionEvent) {
        resourcepackStage.get().show();
    }

    @FXML
    private void handleExport(ActionEvent actionEvent) {

    }

    private static class SmallVariantListCell extends javafx.scene.control.ListCell<VariantModel> {
        @Override
        protected void updateItem(VariantModel variant, boolean empty) {
            super.updateItem(variant, empty);
            if (variant == null) {
                setText(null);
                return;
            }
            setText(variant.nameProperty().get());
        }
    }
}
