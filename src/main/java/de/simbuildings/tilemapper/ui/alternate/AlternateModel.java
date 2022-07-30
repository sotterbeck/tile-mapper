package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.variations.VariantDto;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.inject.Inject;
import java.util.Collection;

public class AlternateModel {
    private final StringProperty material = new SimpleStringProperty();
    private final ObservableList<VariantDto> variantDtos = FXCollections.observableArrayList();
    private final ObjectProperty<VariantDto> defaultVariant = new SimpleObjectProperty<>();

    @Inject
    public AlternateModel() {
    }

    StringProperty materialProperty() {
        return material;
    }

    ObservableList<VariantDto> variantDtos() {
        return FXCollections.unmodifiableObservableList(variantDtos).sorted();
    }

    public ObjectProperty<VariantDto> defaultVariantProperty() {
        return defaultVariant;
    }

    void addVariants(Collection<VariantDto> variantDtos) {
        variantDtos.forEach(this::addVariant);
    }

    private void addVariant(VariantDto variantDto) {
        if (!variantDtos.contains(variantDto)) {
            variantDtos.add(variantDto);
        }
    }
}
