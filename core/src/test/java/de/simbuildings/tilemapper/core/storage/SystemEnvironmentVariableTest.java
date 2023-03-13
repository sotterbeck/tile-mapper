package de.simbuildings.tilemapper.core.storage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SystemEnvironmentVariableTest {

    private EnvironmentVariable underTest;

    @Test
    void shouldGetEnvironmentVariable() {
        // given
        String environmentVariable = "os.name";
        underTest = new SystemEnvironmentVariable();

        // when
        String osName = underTest.get(environmentVariable);

        // then
        assertThat(osName).isEqualTo(System.getenv(environmentVariable));
    }
}