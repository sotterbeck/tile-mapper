package de.simbuildings.tilemapper.ui.controllers;

import de.simbuildings.tilemapper.ui.models.TileModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by SimBuildings on 12.02.22 at 21:32
 */
public class PrimaryController implements Initializable {
    @FXML
    public Button importButton;
    @FXML
    public Label fileLabel;
    @FXML
    public ComboBox<Integer> resolutionComboBox;
    @FXML
    public TextField blockTextField;

    private final TileModel tileModel = new TileModel();

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        blockTextField.textProperty().bind(tileModel.blockNameProperty());
    }
}
