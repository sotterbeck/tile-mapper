package de.simbuildings.tilemapper.ui;

import de.simbuildings.tilemapper.image.SquareImageResolution;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static de.simbuildings.tilemapper.ui.PrimaryController.dataModel;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("components/settingsForm.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: find solution to update preview (use other contoller). Use best practices.

    // TODO: update placeholders (set on initialization with constants)
    @FXML
    public void handleComboBoxAction() {
        if (resolutionComboBox.getValue() == null) {
            return;
        }
        // set resolution and update preview to change grid size
        dataModel.setTargetResolution(new SquareImageResolution(resolutionComboBox.getValue()));

        // reload preview (if possible)
        if (preview != null)
            preview.update();
    }

    public final BooleanBinding filledBinding() {
        return Bindings.isNull(resolutionComboBox.valueProperty()).or(Bindings.isEmpty(blockTextField.textProperty()));
    }

    public void disable() {
        resolutionComboBox.setDisable(true);
        blockTextField.setDisable(true);
        // hide preview if possible
        if (preview != null)
            preview.hide();

    }

    public void enable() {
        resolutionComboBox.setDisable(false);
        blockTextField.setDisable(false);

        resolutionComboBox.getItems().clear();
        for (SquareImageResolution res :
                dataModel.getOriginalResolution().getValuesPowerOfTwoUntilRes()) {
            resolutionComboBox.getItems().add(res.getHeight());
        }
    }

    public void setPreview(TilePreview preview) {
        this.preview = preview;
    }

    public String getBlock() {
        return blockTextField.getText();
    }
}
