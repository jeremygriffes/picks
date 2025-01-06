plugins {
    id("java-library")
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    implementation(project(":server:data"))
    implementation(project(":shared"))

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.firebase.admin)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.datetime)

    testImplementation(libs.kotlin.test.junit)
}