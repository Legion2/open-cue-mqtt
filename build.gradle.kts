plugins {
    kotlin("jvm") version "1.3.72"
    application
}

repositories {
    mavenCentral()
    maven("https://kotlin.bintray.com/ktor")
}

application {
    mainClassName = "io.github.legion2.open_cue_mqtt.MainKt"
    executableDir = ""
    applicationName = "open-cue-mqtt"
}

val ktorVersion = "1.3.2"
val pahoVersion = "1.2.4"
val konfigVersion = "1.6.10.0"
dependencies {
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:$pahoVersion")
    implementation("io.ktor:ktor-client:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-client-gson:$ktorVersion")
    implementation("com.natpryce:konfig:$konfigVersion")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.3"
        apiVersion = "1.3"
        freeCompilerArgs += "-Xopt-in=com.github.ajalt.clikt.completion.ExperimentalCompletionCandidates"
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "6.3"
    distributionType = Wrapper.DistributionType.ALL
}
