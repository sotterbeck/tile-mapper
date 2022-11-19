plugins {
    java
    `maven-publish`
    application
    id("org.openjfx.javafxplugin") version "0.0.12"
    id("org.beryx.jlink") version "2.25.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.simbuildings"
version = "4.0.0-a.2"

project.setProperty("mainClassName", "de.simbuildings.tilemapper.TileMapperApp")

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("net.javacrumbs.json-unit:json-unit-json-path:2.36.0")
    testImplementation("net.javacrumbs.json-unit:json-unit-assertj:2.36.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")

    implementation("net.coobird:thumbnailator:0.4.18")
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.1")
    implementation("org.kordamp.ikonli:ikonli-feather-pack:12.3.1")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.0")
    implementation("net.harawata:appdirs:1.2.1")
    implementation("com.google.dagger:dagger:2.44.2")
    annotationProcessor("com.google.dagger:dagger-compiler:2.44.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

javafx {
    version = "19"
    modules("javafx.controls", "javafx.swing", "javafx.fxml")
}

application {
    mainClass.set("de.simbuildings.tilemapper.TileMapperApp")
    mainModule.set("de.simbuildings.tilemapper")
}

jlink {
    addOptions("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")

    jpackage {
        val currentOperatingSystem = org.gradle.internal.os.OperatingSystem.current()

        icon = "src/main/resources/app_icon.png"

        // fix for semantic versioning
        appVersion = project.version.toString()
            .replaceAfter("-", "")
            .replace("-", "")

        if (currentOperatingSystem.isMacOsX) {
            icon = "src/main/resources/app_icon.icns"
            installerType = "dmg"
        }

        if (currentOperatingSystem.isWindows) {
            icon = "src/main/resources/app_icon.ico"
            installerType = "msi"
            installerOptions = listOf("--win-per-user-install", "--win-dir-chooser", "--win-menu")
        }
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

tasks.jar {
    manifest {
        attributes(mapOf("Main-Class" to "de.simbuildings.tilemapper.App"))
    }
}

tasks.shadowJar {
    manifest {
        attributes(mapOf("Main-Class" to "de.simbuildings.tilemapper.App"))
    }
    minimize()
    mergeServiceFiles()
}
