import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.compose.compiler)
}

kotlin {

    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.animation)
            implementation(compose.materialIconsExtended)
            api(libs.bundles.multiplatform)
            api(compose.components.resources)
            implementation(libs.koinCore)
        }
        androidMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.composeActivity)
            implementation(libs.sqlDelightAndroid)
            implementation(libs.datastore)
            implementation(libs.coreKtx)
            implementation(libs.koinAndroid)
            implementation(libs.koinCompose)
        }

        iosMain.dependencies {
            implementation(libs.sqlDelightNative)
        }
    }
}

android {
    namespace = "ru.dinarastepina.nivkh"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
    kotlin {
        jvmToolchain(11)
    }
}

sqldelight {
    databases {
        create("NivkhDatabase") {
            packageName.set("ru.dinarastepina.database")
            generateAsync.set(true)
        }
    }
}
