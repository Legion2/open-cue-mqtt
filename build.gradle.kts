plugins {
    val kotlinVersion = "1.7.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    application
    id("org.beryx.runtime") version "1.12.7"
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("io.github.legion2.open_cue_mqtt.MainKt")
    executableDir = ""
    applicationName = "open-cue-mqtt"
}

runtime {
    addOptions("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    addModules(
        "java.base",
        "java.sql",//because of gson
        "jdk.unsupported"//https://stackoverflow.com/questions/61727613/unexpected-behaviour-from-gson
    )

    imageZip.set(file("$buildDir/${project.application.applicationName}.zip"))
    targetPlatform("linux-x64") {
        setJdkHome(jdkDownload("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.3%2B7/OpenJDK17U-jdk_x64_linux_hotspot_17.0.3_7.tar.gz"))
    }
    targetPlatform("linux-aarch64") {
        setJdkHome(jdkDownload("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.3%2B7/OpenJDK17U-jdk_aarch64_linux_hotspot_17.0.3_7.tar.gz"))
    }
    targetPlatform("linux-arm32") {
        setJdkHome(jdkDownload("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.3%2B7/OpenJDK17U-jdk_arm_linux_hotspot_17.0.3_7.tar.gz"))
    }
    targetPlatform("windows-x64") {
        setJdkHome(jdkDownload("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.3%2B7/OpenJDK17U-jdk_x64_windows_hotspot_17.0.3_7.zip"))
    }
    targetPlatform("mac-x64") {
        setJdkHome(jdkDownload("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.3%2B7/OpenJDK17U-jdk_x64_mac_hotspot_17.0.3_7.tar.gz"))
    }
    targetPlatform("mac-aarch64") {
        setJdkHome(jdkDownload("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.3%2B7/OpenJDK17U-jdk_aarch64_mac_hotspot_17.0.3_7.tar.gz"))
    }
}

val ktorVersion = "2.0.3"
val pahoVersion = "1.2.5"
val konfigVersion = "1.6.10.0"
dependencies {
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:$pahoVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("com.natpryce:konfig:$konfigVersion")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        languageVersion = "1.7"
        apiVersion = "1.7"
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "7.5"
    distributionType = Wrapper.DistributionType.ALL
}
