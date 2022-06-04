package de.simbuildings.tilemapper.variations;

import de.simbuildings.tilemapper.common.Exportable;
import de.simbuildings.tilemapper.image.TextureImage;
import de.simbuildings.tilemapper.util.PathUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

class TextureExporter implements Exportable {
    private final String material;
    private final Set<TextureImage> textures;

    public TextureExporter(String material, Set<TextureImage> textures) {
        this.material = material;
        this.textures = textures;
    }

    @Override
    public void export(Path destination) throws IOException {
        Path textureDirectory = textureDirectory(destination);
        PathUtils.createDirectories(textureDirectory);
        exportTextures(textureDirectory);
    }

    private void exportTextures(Path destination) throws IOException {
        for (TextureImage textureImage : textures) {
            Path sourceImageFile = textureImage.file();
            Files.copy(sourceImageFile, destination.resolve(textureImage.name() + ".png"));
        }
    }

    @Override
    public Set<Path> conflictFiles(Path destination) {
        return textures.stream()
                .map(texture -> textureDirectory(destination).resolve(texture.name() + ".png"))
                .filter(Files::exists)
                .collect(toUnmodifiableSet());
    }

    private Path textureDirectory(Path destination) {
        return destination.resolve(Paths.get("assets", "minecraft", "textures", "block", material));
    }
}
