package de.simbuildings.tilemapper.gui.common;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;

public class DragAndDropModel {
    private final BooleanProperty isDragging = new SimpleBooleanProperty(false);

    /**
     * Class constructor.
     *
     * @param target the node that modifies the isDraggingProperty.
     */
    public DragAndDropModel(Node target) {
        target.setOnDragEntered(event -> setDraggingProperty(true));
        target.setOnDragExited(event -> setDraggingProperty(false));
        target.setOnDragDropped(event -> setDraggingProperty(false));
    }

    public boolean isDragging() {
        return isDragging.get();
    }

    public BooleanProperty isDraggingProperty() {
        return isDragging;
    }

    public void setDraggingProperty(boolean isDragging) {
        this.isDragging.set(isDragging);
    }
}
