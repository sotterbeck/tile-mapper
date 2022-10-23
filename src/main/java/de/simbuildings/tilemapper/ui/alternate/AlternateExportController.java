package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.resourcepack.Resourcepack;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackListCell;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class AlternateExportController implements Initializable {
    private final AlternateModel alternateModel;
    private final ResourcepackModel resourcepackModel;

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
    public AlternateExportController(AlternateModel alternateModel, ResourcepackModel resourcepackModel) {
        this.alternateModel = alternateModel;
        this.resourcepackModel = resourcepackModel;
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
