package de.simbuildings.tilemapper.core.variations;

import de.simbuildings.tilemapper.core.common.Exportable;
import de.simbuildings.tilemapper.core.image.TextureImage;
import de.simbuildings.tilemapper.core.util.PathUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

class TextureExporter implements Exportable {
    private final String material;
    private final Set<TextureImage> textures;
    private final TextureImage defaultTexture;
    private final String namespace;

    public TextureExporter(String material, Set<TextureImage> textures, TextureImage defaultTexture, String namespace) {
        this.material = material;
        this.namespace = namespace;
        this.textures = textures;
        this.defaultTexture = Optional.ofNullable(defaultTexture)
                .map(textureImage -> textureImage.withName(material))
                .orElse(null);
    }

    public TextureExporter(String material, String namespace, Set<TextureImage> textures) {
        this(material, textures, null, namespace);
    }

    @Override
    public void export(Path destination) throws IOException {
        PathUtils.createDirectories(textureDirectory(destination));
        exportAlternateTextures(destination);
        exportDefaultTexture(destination);
    }

    private void exportAlternateTextures(Path destination) throws IOException {
        for (TextureImage texture : textures) {
            exportTexture(textureDirectory(destination), texture);
        }
    }

    private void exportDefaultTexture(Path destination) throws IOException {
        if (defaultTexture == null) {
            return;
        }
        exportTexture(baseDirectory(destination), defaultTexture);
    }

    private void exportTexture(Path destination, TextureImage texture) throws IOException {
        Path sourceImageFile = texture.file();
        Files.copy(sourceImageFile, destination.resolve(texture.name() + ".png"));
    }

    @Override
    public Set<Path> conflictFiles(Path destination) {
        return textures.stream()
                .map(texture -> textureDirectory(destination).resolve(texture.name() + ".png"))
                .filter(Files::exists)
                .collect(toUnmodifiableSet());
    }

    private Path textureDirectory(Path destination) {
        return destination.resolve(Paths.get("assets", namespace, "textures", "block", material));
    }

    private Path baseDirectory(Path destination) {
        return destination.resolve(Paths.get("assets", namespace, "textures", "block"));
    }
}
