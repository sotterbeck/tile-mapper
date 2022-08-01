package de.simbuildings.tilemapper.image;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class TextureImageTest {

    @Test
    void shouldCreateImage_WhenResolutionPowerOfTwoAndSquare() {
        // given
        Path imagePath = Paths.get("src", "test", "resources", "image", "alternate_sample_1.png");

        // when
        Image image = TextureImage.of(imagePath);

        // then
        assertThat(image.resolution().isPowerOfTwo()).isTrue();
    }

    @Test
    void shouldNotCreateImage_WhenResolutionIsNotPowerOfTwo() {
        // given
        Path imagePath = Paths.get("src", "test", "resources", "image", "tile_sample_failing.png");

        // when
        Throwable thrown = catchThrowable(() -> TextureImage.of(imagePath));

        // then
        assertThat(thrown).isNotNull();
    }

    @Test
    void shouldNotCreateImage_WhenResolutionIsNotSquare() {
        // given
        Path imagePath = Paths.get("src", "test", "resources", "image", "alternate_sample_3.png");

        // when
        Throwable thrown = catchThrowable(() -> TextureImage.of(imagePath));

        // then
        assertThat(thrown).isNotNull();
    }

    @Test
    void name_ShouldReturnFileNameWithoutExtension() {
        // given
        Path imagePath = Paths.get("src", "test", "resources", "image", "alternate_sample_1.png");
        Image image = TextureImage.of(imagePath);

        // when
        String name = image.name();

        // then
        assertThat(name).isEqualTo("alternate_sample_1");
    }
}