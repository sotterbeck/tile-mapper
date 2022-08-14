package de.simbuildings.tilemapper.ui.alternate;

import de.simbuildings.tilemapper.variations.VariantDto;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

import javax.inject.Inject;

public class SelectedVariantModel {
    private final AlternateModel alternateModel;
    private final ObjectProperty<VariantDto> variant = new SimpleObjectProperty<>();
    private final IntegerProperty index = new SimpleIntegerProperty();
    private final IntegerProperty weight = new SimpleIntegerProperty(0);

    @Inject
    public SelectedVariantModel(AlternateModel alternateModel) {
        this.alternateModel = alternateModel;
        variant.addListener((observable, oldVariant, newVariant) -> weight.set(newVariant.weight()));
    }

    public ObjectProperty<VariantDto> variantProperty() {
        return variant;
    }

    public ObservableValue<String> nameProperty() {
        return Bindings.createStringBinding(
                () -> variant.get().defaultTexture().name(),
                variant);
    }

    public IntegerProperty weightProperty() {
        return weight;
    }

    public IntegerProperty indexProperty() {
        return index;
    }

    public void save() {
        VariantDto current = variant.get();
        alternateModel.set(indexProperty().get(), current.withWeight(weight.get()));
    }
}
