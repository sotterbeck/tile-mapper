package de.simbuildings.tilemapper.ui.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class DragAndDropModel {
    private final BooleanProperty isDragging = new SimpleBooleanProperty(false);

    public DragAndDropModel() {
        isDragging.addListener((observable, oldValue, newValue) -> System.out.println(newValue));
    }

    public boolean isIsDragging() {
        return isDragging.get();
    }

    public BooleanProperty isDraggingProperty() {
        return isDragging;
    }

    public void setDraggingProperty(boolean isDragging) {
        this.isDragging.set(isDragging);
    }
}
