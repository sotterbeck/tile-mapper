package de.simbuildings.tilemapper.ui.alternate;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.variations.AlternateTextureExporter;
import de.simbuildings.tilemapper.variations.BlockType;
import de.simbuildings.tilemapper.variations.VariantDto;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

public class AlternateModel {
    private final StringProperty material = new SimpleStringProperty();
    private final ObservableList<VariantDto> variantDtos = FXCollections.observableArrayList();
    private final ObjectProperty<VariantDto> selectedVariant = new SimpleObjectProperty<>();
    private final ObjectProperty<VariantDto> defaultVariant = new SimpleObjectProperty<>();
    private final ObjectMapper objectMapper;

    @Inject
    public AlternateModel(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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

    public ObjectProperty<VariantDto> selectedVariantProperty() {
        return selectedVariant;
    }

    void addVariants(Collection<VariantDto> variantDtos) {
        variantDtos.forEach(this::addVariant);
    }

    private void addVariant(VariantDto variantDto) {
        if (!variantDtos.contains(variantDto)) {
            variantDtos.add(variantDto);
        }
    }

    void removeVariant(VariantDto variantDto) {
        variantDtos.remove(variantDto);
    }

    public void export(Path path, BlockType type) throws IOException {
        Exportable alternateTextureExporter = AlternateTextureExporter.create(objectMapper, material.get(), variantDtoSet(), type);
        alternateTextureExporter.export(path);

    }

    private Set<VariantDto> variantDtoSet() {
        return variantDtos.stream()
                .collect(toUnmodifiableSet());
    }
}
