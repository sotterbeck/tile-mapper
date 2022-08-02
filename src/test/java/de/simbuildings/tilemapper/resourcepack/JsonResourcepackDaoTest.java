package de.simbuildings.tilemapper.resourcepack;

import de.simbuildings.tilemapper.common.DocumentDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;

class JsonResourcepackDaoTest {

    @TempDir
    Path tempDir;
    File jsonFile;

    List<Resourcepack> resourcepacks;

    JsonResourcepackDao underTest;

    @BeforeEach
    void setUp() {
        jsonFile = tempDir.resolve("resourcepacks.json").toFile();
        underTest = new JsonResourcepackDao(jsonFile);
        resourcepacks = List.of(
                new Resourcepack("Default Textures", Path.of("/")),
                new Resourcepack("Faithful", Path.of("/"))
        );
    }


    @Nested
    @DisplayName("Should save resourcepacks")
    class SaveAll_ShouldSaveResourcepacks {
        @Test
        @DisplayName("to new file when it does not exist")
        void ToNewFile_WhenNoFileExists() {
            // given list of resource packs

            // when
            Throwable thrown = catchThrowable(() -> underTest.saveAll(resourcepacks));

            // then
            assertThat(thrown).isNull();
            assertThat(jsonFile).content()
                    .isNotEmpty()
                    .contains("Default Textures");
        }

        @Test
        @DisplayName("to existing file when it exists (override file and its contents)")
        void ToExistingFile_WhenFileExists() throws IOException {
            // given
            underTest.saveAll(resourcepacks);
            List<Resourcepack> newResourcepacks = List.of(
                    new Resourcepack("Super Textures", Path.of("/"))
            );

            // when
            underTest.saveAll(newResourcepacks);

            // then
            assertThat(jsonFile)
                    .isNotEmpty()
                    .content()
                    .doesNotContain("Default Textures")
                    .contains("Super Textures");
        }
    }

    @Test
    @DisplayName("Should find all resourcepacks")
    void findAll_ShouldFindAllResourcepacks() throws IOException {
        // given
        underTest.saveAll(resourcepacks);

        // when
        List<Resourcepack> foundResourcepacks = underTest.findAll();

        // then
        assertThat(foundResourcepacks)
                .hasSize(2);
    }

    @Test
    void findAll_ShouldNotThrowException_WhenFileDoesNotExist() {
        // given
        DocumentDao<Resourcepack> dao = new JsonResourcepackDao(tempDir.resolve("does_not_exist.json").toFile());

        // when
        Throwable thrown = catchThrowable(dao::findAll);

        // then
        assertThat(thrown).isNull();
    }
}