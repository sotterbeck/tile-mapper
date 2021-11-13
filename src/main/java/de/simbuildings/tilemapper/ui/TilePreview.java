package de.simbuildings.tilemapper.ui;

import de.simbuildings.tilemapper.App;
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

import static de.simbuildings.tilemapper.ui.PrimaryController.dataModel;

/**
 * Created by SimBuildings on 09.11.21 at 16:08
 */
public class TilePreview extends VBox {
    @FXML
    private StackPane stackPane;

    public TilePreview() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("components/tilePreview.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // remove old image and grid
        stackPane.getChildren().clear();

        GridPane grid = getGrid();
        ImageView imageView = new ImageView();

        stackPane.getChildren().addAll(imageView, grid);

        try {
            BufferedImage image = Thumbnails.of(dataModel.getImageFile())
                    .size(App.ROOT_WIDTH - 64, 400)
                    .scalingMode(ScalingMode.PROGRESSIVE_BILINEAR)
                    .antialiasing(Antialiasing.OFF).asBufferedImage();
            // show preview
            setVisible(true);
            setManaged(true);

            imageView.setImage(SwingFXUtils.toFXImage(image, null));
            // make grid same width as image
            grid.setMaxWidth(imageView.getImage().getWidth());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hide() {
        stackPane.getChildren().clear();
        setManaged(false);
        setVisible(false);
    }

    private GridPane getGrid() {
        GridPane grid = new GridPane();
        // create columns
        for (int y = 0; y < dataModel.getGridWidth(); y++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100);
            column.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(column);
        }
        // create rows
        for (int i = 0; i < dataModel.getGridHeight(); i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100);
            row.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(row);
        }
        // only for testing (need better solution)
        grid.setGridLinesVisible(true);
        return grid;
    }
}
