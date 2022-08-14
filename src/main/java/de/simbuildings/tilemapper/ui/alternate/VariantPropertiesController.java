package de.simbuildings.tilemapper.ui.alternate;

import javafx.beans.property.ObjectProperty;
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
    private final SelectedVariantModel selectedVariantModel;
    private final ObjectProperty<Integer> weightObjectProperty;

    @FXML
    private Parent root;

    @FXML
    private Spinner<Integer> weightSpinner;
    @FXML
    private Label title;

    @Inject
    public VariantPropertiesController(SelectedVariantModel selectedVariantModel) {
        this.selectedVariantModel = selectedVariantModel;
        this.weightObjectProperty = selectedVariantModel.weightProperty().asObject();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindTitle();
        bindSpinner();
    }

    private void bindSpinner() {
        weightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
        weightSpinner.getValueFactory().valueProperty().bindBidirectional(weightObjectProperty);
    }

    private void bindTitle() {
        title.textProperty().bind(selectedVariantModel.nameProperty());
    }

    @FXML
    public void handleSave(ActionEvent actionEvent) {
        selectedVariantModel.save();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }
}
