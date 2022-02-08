package de.simbuildings.tilemapper.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by SimBuildings on 07.11.21 at 19:44
 */
public class SettingsForm extends VBox {
    @FXML
    public ComboBox<Integer> resolutionComboBox;
    @FXML
    public TextField blockTextField;

    private TilePreview preview;

    public SettingsForm() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("components/settings_form.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPreview(TilePreview preview) {
        this.preview = preview;
    }

    // TODO: ability to enter multiple blocks (generate multiple properties files
    public String getBlock() {
        return blockTextField.getText();
    }
}
