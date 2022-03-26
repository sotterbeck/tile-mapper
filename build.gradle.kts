plugins {
    java
    `maven-publish`
    application
    id("org.openjfx.javafxplugin") version "0.0.12"
    id("org.beryx.jlink") version "2.25.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.simbuildings"
version = "4.0.0-a.1"

project.setProperty("mainClassName", "de.simbuildings.tilemapper.App")

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

javafx {
    version = "18"
    modules("javafx.controls", "javafx.swing", "javafx.fxml")
}

application {
    mainClass.set("de.simbuildings.tilemapper.App")
    mainModule.set("de.simbuildings.tilemapper")
}

jlink {
    addOptions("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")

    launcher {
        name = "TileMapper"
    }

    jpackage {
        val currentOperatingSystem = org.gradle.internal.os.OperatingSystem.current()

        icon = "src/main/resources/app_icon.png"

        imageName = "Tile Mapper"
        installerName = "TileMapper"
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
            installerOptions = listOf("--win-per-user-install", "--win-dir-chooser", "--win-menu")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("commons-io:commons-io:2.11.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    implementation("net.coobird:thumbnailator:0.4.17")
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.0")
    implementation("org.kordamp.ikonli:ikonli-feather-pack:12.3.0")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.2")
    implementation("net.harawata:appdirs:1.2.1")


    implementation("com.google.dagger:dagger:2.41")
    annotationProcessor("com.google.dagger:dagger-compiler:2.41")

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


