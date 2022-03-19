package de.simbuildings.tilemapper.ui.controllers;

import de.simbuildings.tilemapper.ui.models.DragAndDropModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DragAndDropOverlay extends VBox {
    private DragAndDropModel dragAndDropModel;

    public DragAndDropOverlay() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("components/drag_and_drop_overlay.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDragAndDropModel(DragAndDropModel dragAndDropModel) {
        this.dragAndDropModel = dragAndDropModel;
        initializeBindings();
    }

    private void initializeBindings() {
        this.visibleProperty().bind(dragAndDropModel.isDraggingProperty());
    }
}
