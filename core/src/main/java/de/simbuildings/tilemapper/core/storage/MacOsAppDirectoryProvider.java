package de.simbuildings.tilemapper.core.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

class MacOsAppDirectoryProvider implements AppDirectoryProvider {
    @Override
    public Path dataDirectory(String programName) {
        Path home = Paths.get(System.getProperty("user.home"));
        return home.resolve(Paths.get("Library", "Application Support", programName));
    }
}
