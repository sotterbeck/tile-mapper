package de.simbuildings.tilemapper.gui.imagesplitting;

import de.simbuildings.tilemapper.core.tile.TileGrid;
import de.simbuildings.tilemapper.gui.common.UncheckedLoadException;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.Antialiasing;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class TilePreview extends StackPane {

    private static final int SIDE_PADDING = 32;
    private static final int SCENE_WIDTH = 400;

    private TileModel tileModel;

    @FXML
    private ImageView imageView;
    @FXML
    private GridPane gridPane;

    public TilePreview() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/tile_preview.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new UncheckedLoadException(e);
        }

        imageView.imageProperty().addListener((observable, oldImage, newImage) -> gridPane.setMaxWidth(newImage.getWidth()));
    }

    void setTileModel(TileModel tileModel) {
        this.tileModel = tileModel;
        initializeBindings();
    }

    private void initializeBindings() {
        bindVisibleProperty();
        bindImageProperty();
        bindGrid();
    }

    private void bindVisibleProperty() {
        this.visibleProperty().bind(tileModel.originalImageProperty().isNotNull());
        this.managedProperty().bind(tileModel.originalImageProperty().isNotNull());
    }

    private void bindImageProperty() {
        tileModel.originalImageProperty().addListener((observable, oldImage, newImage) -> updateImage(newImage));
    }

    private void updateImage(BufferedImage newImage) {
        try {
            BufferedImage scaledImage = Thumbnails.of(newImage)
                    .size(SCENE_WIDTH - (2 * SIDE_PADDING), 337)
                    .scalingMode(ScalingMode.BILINEAR)
                    .antialiasing(Antialiasing.OFF).asBufferedImage();

            imageView.setImage(SwingFXUtils.toFXImage(scaledImage, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bindGrid() {
        tileModel.tileGridProperty().addListener((observable, oldGrid, newGrid) -> {
            gridPane.getColumnConstraints().clear();
            addColumns(newGrid);

            gridPane.getRowConstraints().clear();
            addRows(newGrid);
        });
    }

    private void addRows(TileGrid newGrid) {
        for (int i = 0; i < newGrid.getHeight(); i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100);
            row.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(row);
        }
    }

    private void addColumns(TileGrid newGrid) {
        for (int y = 0; y < newGrid.getWidth(); y++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100);
            column.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(column);
        }
    }
}
