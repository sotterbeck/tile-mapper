package de.simbuildings.tilemapper.core.storage;

import java.nio.file.Path;

public final class AppDataDirectory implements AppDirectory {
    private final String programName;
    private final AppDirectoryProvider appDirectoryProvider;
    private final EnvironmentVariable environmentVariable;

    private AppDataDirectory(String programName) {
        this(programName, System.getProperty("os.name"), new SystemEnvironmentVariable());
    }

    AppDataDirectory(String programName, String operatingSystem, EnvironmentVariable environmentVariable) {
        this.programName = programName;
        this.environmentVariable = environmentVariable;
        this.appDirectoryProvider = getAppDirectoryProvider(operatingSystem);
    }

    public static AppDataDirectory of(String programName) {
        return new AppDataDirectory(programName);
    }

    private AppDirectoryProvider getAppDirectoryProvider(String operatingSystem) {
        return switch (operatingSystem) {
            case "Mac OS X" -> new MacOsAppDirectoryProvider();
            case "Windows 10" -> new WindowsDirectoryProvider(environmentVariable);
            case "Linux" -> new LinuxAppDirectoryProvider(environmentVariable);
            default -> throw new IllegalArgumentException("operating system not supported: " + operatingSystem);
        };
    }

    @Override
    public Path path() {
        return appDirectoryProvider.dataDirectory(programName);
    }
}
