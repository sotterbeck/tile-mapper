plugins {
    application
    id("org.openjfx.javafxplugin") version "0.0.12"
    id("org.beryx.jlink") version "2.25.0"
}

group = "de.simbuildings"
version = "4.0.0-a.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation("net.coobird:thumbnailator:0.4.18")
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.1")
    implementation("org.kordamp.ikonli:ikonli-feather-pack:12.3.1")
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
    mainClass.set("de.simbuildings.tilemapper.gui.TileMapperApp")
    mainModule.set("de.simbuildings.tilemapper.gui")
}

jlink {
    addOptions("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    addExtraDependencies("javafx")

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

    launcher {
        name = "tile-mapper"
    }
}
