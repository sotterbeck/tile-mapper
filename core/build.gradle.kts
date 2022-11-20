plugins {
    `java-library`
    `maven-publish`
}

group = "de.simbuildings"
version = "4.0.0-a.2"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("net.javacrumbs.json-unit:json-unit-json-path:2.36.0")
    testImplementation("net.javacrumbs.json-unit:json-unit-assertj:2.36.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")

    implementation("net.harawata:appdirs:1.2.1")
    api("com.fasterxml.jackson.core:jackson-databind:2.14.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

publishing {
    repositories {
        maven {
            name = "github-packages"
            url = uri("https://maven.pkg.github.com/simbuildings/tilemapper")
            credentials {
                username = System.getenv("USERNAME")
                password = System.getenv("TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "de.simbuildings"
            artifactId = "tile-mapper"

            from(components["java"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
