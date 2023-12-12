plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.sqlDelight)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()
    jvmToolchain(11)

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"

            }
        }
    }
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
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.animation)
                implementation(compose.materialIconsExtended)
                api(libs.bundles.multiplatform)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                api(compose.components.resources)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.composeActivity)
                implementation(libs.sqlDelightAndroid)
                implementation(libs.datastore)
                implementation(libs.coreKtx)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.sqlDelightNative)
            }
        }
    }
}

android {
    namespace = "ru.dinarastepina.nivkh"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}
dependencies {
    implementation("androidx.core:core-ktx:+")
}

sqldelight {
    databases {
        create("NivkhDatabase") {
            packageName.set("ru.dinarastepina.database")
            generateAsync.set(true)
        }
    }
}