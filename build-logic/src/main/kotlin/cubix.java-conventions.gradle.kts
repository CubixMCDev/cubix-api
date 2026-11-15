// Règles communes à tout le code Java CubixMC : Java 25, NullAway bloquant,
// Error Prone, avertissements = erreurs, formatage automatique, JUnit.

import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone

plugins {
    `java-library`
    id("net.ltgt.errorprone")
    id("com.diffplug.spotless")
}

val libs = the<VersionCatalogsExtension>().named("libs")
fun lib(alias: String) = libs.findLibrary(alias).get()
fun version(alias: String) = libs.findVersion(alias).get().requiredVersion

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(version("java"))
    }
}

dependencies {
    compileOnly(lib("jspecify"))
    testCompileOnly(lib("jspecify"))
    errorprone(lib("errorprone-core"))
    errorprone(lib("nullaway"))

    testImplementation(platform(lib("junit-bom")))
    testImplementation(lib("junit-jupiter"))
    testImplementation(lib("assertj"))
    testRuntimeOnly(lib("junit-launcher"))
}

tasks.withType<JavaCompile>().configureEach {
    // spotlessApply réécrit les sources : compiler en même temps fait lire à Error Prone un
    // fichier qui change sous ses yeux (plantage IndexOutOfBounds dans getSourceCode).
    mustRunAfter("spotlessApply")
    options.encoding = "UTF-8"
    options.release = version("java").toInt()
    options.compilerArgs.addAll(listOf("-Xlint:all,-processing,-serial", "-Werror", "-parameters"))
    // Error Prone a besoin d'accéder aux internes de javac : un compilateur à part
    // permet au plugin de passer lui-même les --add-exports nécessaires.
    options.isFork = true
    options.errorprone {
        disableWarningsInGeneratedCode = true
        check("NullAway", CheckSeverity.ERROR)
        option("NullAway:AnnotatedPackages", "fr.cubixmc")
        option("NullAway:JSpecifyMode", "true")
    }
}

// Les tests manipulent volontairement des valeurs nulles : NullAway n'y est pas utile.
tasks.named<JavaCompile>("compileTestJava") {
    options.errorprone.check("NullAway", CheckSeverity.OFF)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
        events("failed", "skipped")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

tasks.withType<Javadoc>().configureEach {
    options.encoding = "UTF-8"
    (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:all,-missing", "-quiet")
}

spotless {
    java {
        palantirJavaFormat(version("palantir-java-format"))
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
    kotlinGradle {
        target("*.gradle.kts")
        trimTrailingWhitespace()
        endWithNewline()
    }
}
