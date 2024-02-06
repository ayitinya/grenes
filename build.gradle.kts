plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false

    alias(libs.plugins.kotlinPluginSerialization) apply false

    id("com.google.gms.google-services") version "4.4.0" apply false
    id("org.kodein.mock.mockmp") version "1.16.0" apply false
    id("app.cash.sqldelight") version "2.0.1" apply false
}

tasks.wrapper {
    gradleVersion = "8.5"
    distributionType = Wrapper.DistributionType.ALL
}