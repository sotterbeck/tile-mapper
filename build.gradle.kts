plugins {
    java
    `maven-publish`
    application
    id("org.openjfx.javafxplugin") version "0.0.12"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "de.simbuildings"
version = "3.3.0"

project.setProperty("mainClassName", "de.simbuildings.tilemapper.App")

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.swing", "javafx.fxml")
}

application {
    mainClass.set("de.simbuildings.tilemapper.App")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("commons-io:commons-io:2.11.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    implementation("net.coobird:thumbnailator:0.4.16")
    implementation("org.kordamp.ikonli:ikonli-javafx:12.2.0")
    implementation("org.kordamp.ikonli:ikonli-feather-pack:12.2.0")
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
            version = version

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
    mergeServiceFiles()
}


