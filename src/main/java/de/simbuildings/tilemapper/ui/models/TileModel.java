package de.simbuildings.tilemapper.ui.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by SimBuildings on 12.02.22 at 21:41
 */
public class TileModel {
    private final IntegerProperty targetResolution = new SimpleIntegerProperty();
    private final StringProperty blockName = new SimpleStringProperty();

    public IntegerProperty targetResolutionProperty() {
        return targetResolution;
    }

    public StringProperty blockNameProperty() {
        return blockName;
    }

    public void setTargetResolution(int targetResolution) {
        this.targetResolution.set(targetResolution);
    }
}
