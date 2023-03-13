package de.simbuildings.tilemapper.core.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppDirectoryProviderTest {
    private Path macDataDirectory;
    private Path linuxDefaultDataDirectory;
    private String programName;

    private EnvironmentVariable systemEnvironmentVariable;
    @Mock
    private EnvironmentVariable mockedEnvironmentVariable;

    @BeforeEach
    void setUp() {
        Path home = Paths.get(System.getProperty("user.home"));
        macDataDirectory = home.resolve(Paths.get("Library", "Application Support", "TileMapper"));
        linuxDefaultDataDirectory = home.resolve(Paths.get(".local", "share", "TileMapper"));
        programName = "TileMapper";
        systemEnvironmentVariable = new SystemEnvironmentVariable();
    }

    @Test
    void shouldGetDataDirectoryOnMac() {
        // given
        String operatingSystem = "Mac OS X";
        AppDirectory appDirectory = new AppDataDirectory(programName, operatingSystem, systemEnvironmentVariable);

        // when
        Path dataDir = appDirectory.path();

        // then
        assertThat(dataDir).isEqualTo(macDataDirectory);
    }

    @Test
    void shouldGetDataDirectoryOnWindows() {
        // given
        String operatingSystem = "Windows 10";

        // when
        when(mockedEnvironmentVariable.get("APPDATA")).thenReturn("AppData");
        AppDirectory appDirectory = new AppDataDirectory(programName, operatingSystem, mockedEnvironmentVariable);
        Path dataDir = appDirectory.path();

        // then
        assertThat(dataDir).isEqualTo(Paths.get("AppData", "Local", "TileMapper"));
    }

    @Nested
    class OnLinux {
        // given
        private final String operatingSystem = "Linux";

        @Test
        void shouldGetDefaultDataDirectory_WhenNoXDGHomeIsSet() {
            // when
            when(mockedEnvironmentVariable.get("XDG_DATA_HOME")).thenReturn(null);
            AppDirectory appDirectory = new AppDataDirectory(programName, operatingSystem, mockedEnvironmentVariable);
            Path dataDir = appDirectory.path();

            // then
            assertThat(dataDir).isEqualTo(linuxDefaultDataDirectory);
        }

        @Test
        void shouldGetXDGDataHomeOnLinux_WhenXDGHomeIsSet() {
            // when
            when(mockedEnvironmentVariable.get("XDG_DATA_HOME")).thenReturn("/var/lib");
            AppDirectory appDirectory = new AppDataDirectory(programName, operatingSystem, mockedEnvironmentVariable);
            Path dataDir = appDirectory.path();

            // then
            assertThat(dataDir).isEqualTo(Paths.get("/", "var", "lib", "TileMapper"));
        }
    }

    @Test
    void shouldThrowExceptionIfOperatingSystemDoesNotExist() {
        // given
        String invalidOs = "Freax";
        String programName = "TileMapper";

        // when
        Throwable thrown = catchThrowable(() -> new AppDataDirectory(programName, invalidOs, systemEnvironmentVariable));

        // then
        assertThat(thrown).isNotNull();
    }

    @Nested
    class ShouldProvideDirectoryAutomaticallyOn {
        @Test
        @EnabledOnOs(OS.MAC)
        void mac() {
            // given
            AppDirectory appDirectory = AppDataDirectory.of(programName);

            // when
            Path dataDir = appDirectory.path();

            // then
            assertThat(dataDir).isEqualTo(macDataDirectory);
        }

        @Test
        @EnabledOnOs(OS.WINDOWS)
        void windows() {
            // given
            AppDirectory appDirectory = AppDataDirectory.of(programName);

            // when
            Path dataDir = appDirectory.path();

            // then
            assertThat(dataDir).isEqualTo(windowsDataDirectory());
        }

        @Test
        @EnabledOnOs(OS.LINUX)
        void linux() {
            // given
            AppDirectory appDirectory = AppDataDirectory.of(programName);

            // when
            Path dataDir = appDirectory.path();

            // then
            assertThat(dataDir).isEqualTo(linuxDefaultDataDirectory);
        }
    }

    private static Path windowsDataDirectory() {
        Path appData = Paths.get(System.getenv("APPDATA"));
        return appData.resolve(Paths.get("Local", "TileMapper"));
    }
}
