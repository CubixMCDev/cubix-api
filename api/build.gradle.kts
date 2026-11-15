plugins {
    id("cubix.java-conventions")
    alias(libs.plugins.maven.publish)
}

description = "API publique CubixMC : ce que voit un module, et rien d'autre."

dependencies {
    // Exposées aux modules : elles font partie des signatures de l'API.
    api(libs.adventure.api)
    api(libs.slf4j.api)
    api(libs.jspecify)
}

tasks.processResources {
    val apiVersion = project.version.toString()
    inputs.property("version", apiVersion)
    filesMatching("**/cubix-api.properties") {
        expand("version" to apiVersion)
    }
}

mavenPublishing {
    coordinates("fr.cubixmc", "cubix-api", project.version.toString())
    publishToMavenCentral()
    // Signature uniquement quand la clé est fournie (CI de publication) :
    // publishToMavenLocal reste utilisable sans rien configurer.
    if (providers.gradleProperty("signingInMemoryKey").isPresent) {
        signAllPublications()
    }
    pom {
        name = "CubixMC API"
        description = project.description
        url = "https://github.com/CubixMCDev/cubix-api"
        inceptionYear = "2026"
        licenses {
            license {
                name = "Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "cubixmc"
                name = "CubixMC"
                url = "https://cubixmc.fr"
            }
        }
        scm {
            url = "https://github.com/CubixMCDev/cubix-api"
            connection = "scm:git:https://github.com/CubixMCDev/cubix-api.git"
            developerConnection = "scm:git:ssh://git@github.com/CubixMCDev/cubix-api.git"
        }
    }
}
