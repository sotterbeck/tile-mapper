package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.image.TextureImage;
import de.simbuildings.tilemapper.util.PathUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

class TextureExporter implements Exportable {
    private final String material;
    private final Map<String, TextureImage> imageResources;
    private final Set<TextureImage> additionalTextures;

    public TextureExporter(String material, Map<String, TextureImage> imageResources) {
        this.material = material;
        this.imageResources = imageResources;
        additionalTextures = Collections.emptySet();
    }

    public TextureExporter(String material, Map<String, TextureImage> imageResources, Set<TextureImage> additional) {
        this.material = material;
        this.imageResources = imageResources;
        additionalTextures = additional;
    }

    @Override
    public void export(Path destination) throws IOException {
        Path textureDirectory = destination.resolve(Paths.get("assets", "minecraft", "textures", "block", material));
        PathUtils.createDirectories(textureDirectory);

        exportDefaultTextures(textureDirectory);
        exportAdditionalTextures(textureDirectory);
    }

    private void exportDefaultTextures(Path destination) throws IOException {
        for (Map.Entry<String, TextureImage> imageEntry : imageResources.entrySet()) {
            Path sourceImageFile = imageEntry.getValue().file();
            String variant = imageEntry.getKey();

            Files.copy(sourceImageFile, destination.resolve(variant + ".png"));
        }
    }

    private void exportAdditionalTextures(Path destination) throws IOException {
        for (TextureImage additionalTexture : additionalTextures) {
            Path sourceImageFile = additionalTexture.file();
            String fileName = sourceImageFile.getFileName().toString();
            Files.copy(sourceImageFile, destination.resolve(fileName));
        }
    }

    @Override
    public boolean hasConflict(Path destination) {
        return false;
    }

    @Override
    public Set<Path> conflictFiles(Path destination) {
        return Collections.emptySet();
    }
}
