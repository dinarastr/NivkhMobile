import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.compose.compiler)
}

kotlin {

    androidTarget()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
            
            // Add SQLite linker option
            linkerOpts.add("-lsqlite3")
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
            implementation(compose.components.resources)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }
        androidMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.composeActivity)
            implementation(libs.sqlDelightAndroid)
            implementation(libs.datastore)
            implementation(libs.coreKtx)
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
    linkSqlite.set(true)
}
