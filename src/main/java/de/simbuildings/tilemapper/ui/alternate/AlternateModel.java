package de.simbuildings.tilemapper.ui.alternate;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.image.TextureImage;
import de.simbuildings.tilemapper.variations.AlternateTextureExporter;
import de.simbuildings.tilemapper.variations.BlockType;
import de.simbuildings.tilemapper.variations.VariantDto;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
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
    private final ObjectProperty<TextureImage> defaultTexture = new SimpleObjectProperty<>();
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

    public ObjectProperty<TextureImage> defaultTextureProperty() {
        return defaultTexture;
    }

    void add(Collection<VariantDto> variantDtos) {
        variantDtos.forEach(this::addVariant);
    }

    private void addVariant(VariantDto newVariant) {
        boolean containsVariant = variantDtos.stream()
                .anyMatch(variant -> variant.defaultTexture().name().equals(newVariant.defaultTexture().name()));
        if (!containsVariant) {
            variantDtos.add(newVariant);
        }
    }

    void remove(VariantDto variantDto) {
        variantDtos.remove(variantDto);
    }

    public void set(int index, VariantDto updated) {
        variantDtos.set(index, updated);
    }

    public void export(Path path, BlockType type) throws IOException {
        Exportable alternateTextureExporter = AlternateTextureExporter.builder(objectMapper, material.get(), variantDtoSet())
                .blockTypes(Set.of(type))
                .build();
        alternateTextureExporter.export(path);

    }

    private Set<VariantDto> variantDtoSet() {
        return variantDtos.stream()
                .collect(toUnmodifiableSet());
    }

    public static class SelectedVariantModel {
        private final AlternateModel alternateModel;

        private final ObjectProperty<VariantDto> variant = new SimpleObjectProperty<>();
        private final IntegerProperty index = new SimpleIntegerProperty();
        private final IntegerProperty weight = new SimpleIntegerProperty(0);

        @Inject
        public SelectedVariantModel(AlternateModel alternateModel) {
            this.alternateModel = alternateModel;
            variant.addListener(this::updateVariantProperties);
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

        private void updateVariantProperties(ObservableValue<? extends VariantDto> observable, VariantDto oldVariant, VariantDto newVariant) {
            if (newVariant == null) {
                return;
            }
            weight.set(newVariant.weight());
        }
    }
}
