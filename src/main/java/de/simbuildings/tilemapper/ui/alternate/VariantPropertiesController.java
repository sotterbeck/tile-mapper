package de.simbuildings.tilemapper.ui.alternate;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;

public class VariantPropertiesController {
    private final AlternateModel alternateModel;

    @FXML
    private Label title;

    @Inject
    public VariantPropertiesController(AlternateModel alternateModel) {
        this.alternateModel = alternateModel;
    }
}
