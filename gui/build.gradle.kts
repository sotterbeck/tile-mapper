plugins {
    id("de.simbuildings.tilemapper.java-conventions")
    application
    id("org.openjfx.javafxplugin") version "0.0.14"
    id("org.beryx.jlink") version "2.26.0"
}

dependencies {
    implementation(project(":core"))
    implementation("net.coobird:thumbnailator:0.4.19")
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.1")
    implementation("org.kordamp.ikonli:ikonli-feather-pack:12.3.1")
    implementation("com.google.dagger:dagger:2.47")
    implementation("com.google.guava:guava:32.1.1-jre")
    annotationProcessor("com.google.dagger:dagger-compiler:2.47")
}

javafx {
    version = "20"
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
