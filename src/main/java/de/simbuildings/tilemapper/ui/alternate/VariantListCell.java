package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.common.UncheckedLoadException;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

import java.io.IOException;

final class VariantListCell extends ListCell<VariantModel> {
    @FXML
    private Parent root;
    @FXML
    private ImageView imageView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label weightLabel;

    @Override
    protected void updateItem(VariantModel variant, boolean isEmpty) {
        super.updateItem(variant, isEmpty);
        if (variant == null || isEmpty) {
            setText(null);
            setGraphic(null);
            return;
        }

        loadFxml();
        setText(null);

        nameLabel.textProperty().bind(variant.nameProperty());
        weightLabel.textProperty().bind(Bindings.concat("Weight ", variant.weightProperty()));
        imageView.imageProperty().bind(variant.imageProperty());
        setGraphic(root);
    }

    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/variant_list_cell.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new UncheckedLoadException(e);
        }
    }
}
