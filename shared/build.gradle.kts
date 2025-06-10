import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.compose.compiler)
}

kotlin {

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
                freeCompilerArgs += listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-Xjvm-default=all",
                    "-Xno-param-assertions",
                    "-Xno-call-assertions",
                    "-Xno-receiver-assertions"
                )
            }
        }
    }
    
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
            
            // Optimize for release
            freeCompilerArgs += listOf(
                "-Xdisable-phases=VerifyBitcode",
                "-Xlazy-ir-for-caches=disable"
            )
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.animation)
            api(libs.bundles.multiplatform)
            implementation(compose.components.resources)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.voyager.screenmodel)
            implementation(libs.voyager.koin)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)


        }
        androidMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.composeActivity)
            implementation(libs.sqlDelightAndroid)
            implementation(libs.datastore)
            implementation(libs.coreKtx)
            implementation(libs.ktor.client.android)
        }

        iosMain.dependencies {
            implementation(libs.sqlDelightNative)
            implementation(libs.ktor.client.darwin)
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
    
    buildTypes {
        release {
            isMinifyEnabled = false
            consumerProguardFiles("consumer-rules.pro")
        }
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
