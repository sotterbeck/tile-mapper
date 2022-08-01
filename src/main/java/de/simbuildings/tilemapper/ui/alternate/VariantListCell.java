package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.common.UncheckedLoadException;
import de.simbuildings.tilemapper.variations.VariantDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

final class VariantListCell extends ListCell<VariantDto> {
    @FXML
    private Parent root;
    @FXML
    private ImageView imageView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label weightLabel;

    @Override
    protected void updateItem(VariantDto variant, boolean isEmpty) {
        super.updateItem(variant, isEmpty);
        if (variant == null || isEmpty) {
            setText(null);
            setGraphic(null);
            return;
        }

        loadFxml();
        setText(null);

        nameLabel.setText(variant.defaultTexture().name());
        weightLabel.setText("weight " + variant.weight());
        imageView.setImage(getImage(variant));
        setGraphic(root);
    }

    private Image getImage(VariantDto variant) {
        Image image;
        try {
            Path imagePath = variant.defaultTexture().file();
            FileInputStream fileInputStream = new FileInputStream(imagePath.toFile());
            image = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
        return image;
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
