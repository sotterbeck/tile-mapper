package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.resourcepack.Resourcepack;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackListCell;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;
import de.simbuildings.tilemapper.variations.VariantDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;

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

    @Inject
    public AlternateExportController(AlternateModel alternateModel, ResourcepackModel resourcepackModel) {
        this.alternateModel = alternateModel;
        this.resourcepackModel = resourcepackModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourcepackComboBox.setItems(resourcepackModel.resourcepacksProperty());
        resourcepackComboBox.setCellFactory(param -> new ResourcepackListCell());
        resourcepackComboBox.setButtonCell(new ResourcepackListCell());

        defaultTextureComboBox.setItems(alternateModel.variantDtoList());
        defaultTextureComboBox.setCellFactory(param -> new VariantDtoListCell());
        defaultTextureComboBox.setButtonCell(new VariantDtoListCell());
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
