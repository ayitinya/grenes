import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)

    alias(libs.plugins.kotlinPluginSerialization)
    id("org.kodein.mock.mockmp")
    id("app.cash.sqldelight")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    listOf(
        iosX64(), iosArm64(), iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.multiplatform.settings.datastore)
            implementation(libs.accompanist.systemuicontroller)

            implementation(libs.koin.android)

            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:32.7.0"))

            // Add the dependency for the Firebase Authentication library
            // When using the BoM, you don't specify versions in Firebase library dependencies
            implementation("com.google.firebase:firebase-auth")

            // Also add the dependency for the Google Play services library and specify its version
            implementation(libs.gms.play.services.auth)
            implementation("com.google.firebase:firebase-dynamic-links")

            api("androidx.startup:startup-runtime:1.1.1")

            implementation("app.cash.sqldelight:android-driver:2.0.1")
        }

        commonTest.dependencies {
            implementation(libs.koin.test)
            implementation(libs.multiplatform.settings.test)
            implementation(libs.kotlin.test.junit)

            implementation("app.cash.paging:paging-testing:3.3.0-alpha02-0.4.0")
        }

        commonMain.dependencies {
            implementation(projects.shared)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            @OptIn(ExperimentalComposeLibrary::class) implementation(compose.components.resources)

            implementation(compose.materialIconsExtended)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.resources)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.serialization.kotlinx.json)


            implementation(libs.insert.koin.koin.core)
            implementation(libs.koin.compose)

            implementation(libs.precompose)
            implementation(libs.precompose.viewmodel)
            implementation(libs.precompose.koin)

            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.serialization)

            implementation(libs.material3.window.sizeclass.multiplatform)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)


            implementation(libs.firebase.auth)

            implementation("app.cash.paging:paging-common:3.3.0-alpha02-0.4.0")
            implementation("app.cash.paging:paging-compose-common:3.3.0-alpha02-0.4.0")

            implementation("media.kamel:kamel-image:0.9.0")

            implementation("org.jetbrains.kotlin:kotlin-reflect")

            api("androidx.datastore:datastore-preferences-core:1.1.0-alpha07")
            api("androidx.datastore:datastore-core:1.1.0-alpha07")

        }

        iosMain.dependencies {
            implementation("app.cash.sqldelight:native-driver:2.0.1")
        }
    }
}

mockmp {
    usesHelper = true
    installWorkaround()
}

android {
    namespace = "me.ayitinya.grenes"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "me.ayitinya.grenes"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }

    apply(plugin = "com.google.gms.google-services")
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("me.ayitinya.grenes")
        }
    }
}

