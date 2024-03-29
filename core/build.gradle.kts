plugins {
    id("de.simbuildings.tilemapper.java-conventions")
    `java-library`
    `maven-publish`
}

dependencies {
    testImplementation("net.javacrumbs.json-unit:json-unit-json-path:2.36.1")
    testImplementation("net.javacrumbs.json-unit:json-unit-assertj:2.36.1")

    api("com.fasterxml.jackson.core:jackson-databind:2.15.0")
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
