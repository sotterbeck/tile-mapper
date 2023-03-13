package de.simbuildings.tilemapper.core.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

class LinuxAppDirectoryProvider implements AppDirectoryProvider {
    private final EnvironmentVariable environmentVariable;

    LinuxAppDirectoryProvider(EnvironmentVariable environmentVariable) {
        this.environmentVariable = environmentVariable;
    }

    @Override
    public Path dataDirectory(String programName) {
        if (environmentVariable.get("XDG_DATA_HOME") == null) {
            return getDefaultDirectory(programName);
        }
        Path xdgDataHome = Paths.get(environmentVariable.get("XDG_DATA_HOME"));
        return xdgDataHome.resolve(programName);
    }

    private Path getDefaultDirectory(String programName) {
        Path home = Paths.get(System.getProperty("user.home"));
        return home.resolve(Paths.get(".local", "share", programName));
    }
}
