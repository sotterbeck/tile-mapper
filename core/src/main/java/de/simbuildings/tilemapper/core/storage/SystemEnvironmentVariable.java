package de.simbuildings.tilemapper.core.storage;

class SystemEnvironmentVariable implements EnvironmentVariable {
    @Override
    public String get(String environmentVariable) {
        return System.getenv(environmentVariable);
    }
}
