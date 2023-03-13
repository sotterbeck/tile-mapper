package de.simbuildings.tilemapper.core.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

class WindowsDirectoryProvider implements AppDirectoryProvider {
    private final EnvironmentVariable environmentVariable;

    WindowsDirectoryProvider(EnvironmentVariable environmentVariable) {
        this.environmentVariable = environmentVariable;
    }

    @Override
    public Path dataDirectory(String programName) {
        Path appData = Paths.get(environmentVariable.get("APPDATA"));
        return appData.resolve(Paths.get("Local", "TileMapper"));
    }
}
