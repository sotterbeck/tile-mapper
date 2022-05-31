package de.simbuildings.tilemapper.image;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class TextureImageTest {

    @Test
    void shouldCreateImage_WhenResolutionIsPowerOfTwo() {
        // given
        Path imagePath = Paths.get("src", "test", "resources", "image", "tile_sample_working.png");

        // when
        TextureImage textureImage = TextureImage.of(imagePath);

        // then
        assertThat(textureImage.resolution().isPowerOfTwo()).isTrue();
    }

    @Test
    void shouldNotCreateTextureImage_WhenResolutionIsPowerOfTwo() {
        // given
        Path imagePath = Paths.get("src", "test", "resources", "image", "tile_sample_failing.png");

        // when
        Throwable thrown = catchThrowable(() -> TextureImage.of(imagePath));

        // then
        assertThat(thrown).isNotNull();
    }

    @Test
    void name_ShouldReturnFileNameWithoutExtension() {
        // given
        Path imagePath = Paths.get("src", "test", "resources", "image", "tile_sample_working.png");
        Image image = TextureImage.of(imagePath);

        // when
        String name = image.name();

        // then
        assertThat(name).isEqualTo("tile_sample_working");
    }
}
