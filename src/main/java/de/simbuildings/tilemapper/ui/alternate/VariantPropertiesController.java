package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.image.Image;
import de.simbuildings.tilemapper.variations.VariantDto;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import javax.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class VariantPropertiesController implements Initializable {
    private final AlternateModel alternateModel;

    @FXML
    private Spinner<Integer> weightSpinner;
    @FXML
    private Label title;

    @Inject
    public VariantPropertiesController(AlternateModel alternateModel) {
        this.alternateModel = alternateModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindTitle();
        bindSpinner();
    }

    private void bindSpinner() {
        weightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
    }

    private void bindTitle() {
        title.textProperty().bind(Bindings.createStringBinding(
                () -> selectedVariant()
                        .map(VariantDto::defaultTexture)
                        .map(Image::name)
                        .orElse(""),
                alternateModel.selectedVariantProperty()));
    }

    private Optional<VariantDto> selectedVariant() {
        return Optional.ofNullable(alternateModel.selectedVariantProperty().get());
    }
}
