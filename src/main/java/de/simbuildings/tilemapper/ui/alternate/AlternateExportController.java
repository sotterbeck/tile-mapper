package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.resourcepack.Resourcepack;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackListCell;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;
import de.simbuildings.tilemapper.variations.VariantDto;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class AlternateExportController implements Initializable {
    private final AlternateModel alternateModel;
    private final ResourcepackModel resourcepackModel;

    @FXML
    private ComboBox<VariantDto> defaultTextureComboBox;
    @FXML
    private CheckBox blockCheckBox;
    @FXML
    private CheckBox stairsCheckBox;
    @FXML
    private CheckBox slabsCheckBox;
    @FXML
    private ComboBox<Resourcepack> resourcepackComboBox;
    @FXML
    private Button exportButton;

    @Inject
    public AlternateExportController(AlternateModel alternateModel, ResourcepackModel resourcepackModel) {
        this.alternateModel = alternateModel;
        this.resourcepackModel = resourcepackModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindResourcepacks();
        bindDefaultTexture();
    }

    private void bindDefaultTexture() {
        defaultTextureComboBox.setItems(alternateModel.variantDtos());
        defaultTextureComboBox.setCellFactory(param -> new VariantDtoListCell());
        defaultTextureComboBox.setButtonCell(new VariantDtoListCell());

        SingleSelectionModel<VariantDto> selectionModel = defaultTextureComboBox.getSelectionModel();
        selectionModel.selectFirst();
        selectionModel.selectedItemProperty().addListener(this::setDefaultTexture);

    }

    private void bindResourcepacks() {
        resourcepackComboBox.setItems(resourcepackModel.resourcepacksProperty());
        resourcepackComboBox.setCellFactory(param -> new ResourcepackListCell());
        resourcepackComboBox.setButtonCell(new ResourcepackListCell());
    }

    public void handleExport(ActionEvent actionEvent) {

    }

    private void setDefaultTexture(ObservableValue<? extends VariantDto> observable, VariantDto oldVariant, VariantDto newVariant) {
        if (newVariant == null) {
            return;
        }
        alternateModel.defaultTextureProperty().set(newVariant.defaultTexture());
    }

    private static class VariantDtoListCell extends ListCell<VariantDto> {
        @Override
        protected void updateItem(VariantDto variantDto, boolean empty) {
            super.updateItem(variantDto, empty);
            if (isEmpty() || variantDto == null) {
                setText(null);
                return;
            }
            setText(variantDto.defaultTexture().name());
        }
    }
}
