package de.simbuildings.tilemapper.ui.alternate;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.Collection;

public class AlternateModel {
    private final ObjectMapper objectMapper;

    private final StringProperty material = new SimpleStringProperty();
    private final ObservableList<VariantModel> variants = FXCollections.observableArrayList();

    @Inject
    public AlternateModel(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ObservableList<VariantModel> variants() {
        return FXCollections.unmodifiableObservableList(variants).sorted();
    }

    public StringProperty materialProperty() {
        return material;
    }

    public void addVariants(Collection<Path> imagePaths) {
        imagePaths.stream()
                .map(VariantModel::new)
                .forEach(this::addVariantIfNotExists);
    }

    private void addVariantIfNotExists(VariantModel newVariant) {
        boolean variantExists = variants.stream()
                .anyMatch(variantModel -> variantModel.nameProperty().get().equals(newVariant.nameProperty().get()));
        if (variantExists) {
            return;
        }
        variants.add(newVariant);
    }
}
