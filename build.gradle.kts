plugins {
    kotlin("jvm") version "2.0.21"
    id("application")
}

group = "io.github.realmtrx"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.kord:kord-core:0.18.1")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.1.4")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    implementation("ch.qos.logback:logback-classic:1.5.13")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

application {
    mainClass.set("MainKt")
}

sourceSets {
    main {
        kotlin {
            setSrcDirs(listOf("src"))
            exclude("resources")
        }
        resources {
            setSrcDirs(listOf("src/resources"))
        }
    }
}

kotlin {
    jvmToolchain(21)
}
