package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.resourcepack.Resourcepack;
import de.simbuildings.tilemapper.ui.resourcepack.ResourcepackModel;
import de.simbuildings.tilemapper.variations.VariantDto;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import javax.inject.Inject;

public class AlternateExportController {
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
    private ComboBox<Resourcepack> exportLocationComboBox;

    @Inject
    public AlternateExportController(AlternateModel alternateModel, ResourcepackModel resourcepackModel) {
        this.alternateModel = alternateModel;
        this.resourcepackModel = resourcepackModel;
    }
}
