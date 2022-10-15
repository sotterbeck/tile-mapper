package de.simbuildings.tilemapper.ui.alternate;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class VariantPropertiesController implements Initializable {
    private final ObjectProperty<VariantModel> selectedVariant;

    @FXML
    private Parent root;
    @FXML
    private Label title;
    @FXML
    private Spinner<Integer> weightSpinner;


    @Inject
    public VariantPropertiesController(ObjectProperty<VariantModel> selectedVariant) {
        this.selectedVariant = selectedVariant;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.textProperty().bind(Bindings.selectString(selectedVariant, "name"));

        weightSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
        selectedVariant.addListener(this::updateWeightSpinner);
    }

    @FXML
    private void handleSave(ActionEvent actionEvent) {
        selectedVariant.get().weightProperty().set(weightSpinner.getValue());
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    private void updateWeightSpinner(ObservableValue<? extends VariantModel> observable,
                                     VariantModel oldValue,
                                     VariantModel newValue) {
        if (newValue == null) {
            return;
        }
        weightSpinner.getValueFactory().valueProperty().set(newValue.weightProperty().get());
    }
}
