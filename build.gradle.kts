import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
    id("fabric-loom") version "1.9.2"
    id("maven-publish")
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
    archivesName.set(project.property("archives_base_name") as String)
}

val targetJavaVersion = 21
java {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}


repositories {
    maven {
        name = "CottonMC"
        url = uri("https://server.bbkr.space/artifactory/libs-release")
        content {
            includeGroup("io.github.cottonmc")
        }
    }

    maven {
        url = uri("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
    }

    maven {
        url = uri("https://maven.wispforest.io/releases/")
    }

    maven {
        name = "Jitpack"
        url = uri("https://jitpack.io")
    }
}

fun property(s: String): Any? =
    project.property(s)

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${property("kotlin_loader_version")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")

    modRuntimeOnly("me.djtheredstoner:DevAuth-fabric:${property("devauth_version")}")

    modImplementation(include("io.github.0x3c50.renderer:renderer-fabric:1.2.5")!!)

    modImplementation("io.wispforest:owo-lib:${property("owo_version")}")
    include("io.wispforest:owo-sentinel:${property("owo_version")}")

    implementation("io.github.revxrsal:lamp.common:${property("lamp_version")}")
    implementation("com.github.JnCrMx:discord-game-sdk4j:${property("discord_game_sdk4j_version")}")

    modImplementation(include(kotlin("reflect"))!!)

    modImplementation(include("com.github.meo209:KeventBus:${property("keventbus_version")}")!!)
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("minecraft_version", project.property("minecraft_version"))
    inputs.property("loader_version", project.property("loader_version"))
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "minecraft_version" to project.property("minecraft_version"),
            "loader_version" to project.property("loader_version"),
            "kotlin_loader_version" to project.property("kotlin_loader_version")
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    // ensure that the encoding is set to UTF-8, no matter what the system default is
    // this fixes some edge cases with special characters not displaying correctly
    // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
    // If Javadoc is generated, this must be specified in that task too.
    options.encoding = "UTF-8"
    options.release.set(targetJavaVersion)
    options.compilerArgs.add("-parameters")
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(targetJavaVersion.toString()))
    compilerOptions {
        javaParameters = true
    }
}

tasks.withType<KotlinJvmCompile> {
    compilerOptions {
        javaParameters = true
    }
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesName}" }
    }
}

// configure the maven publication
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.property("archives_base_name") as String
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}
