plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinPluginSerialization)
    application
}

group = "me.ayitinya.grenes"
version = "1.0.0"
application {
    mainClass.set("me.ayitinya.grenes.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["development"] ?: "false"}")
}

val exposedVersion = "0.41.1"
val h2Version = "2.1.214"

dependencies {
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.exposed.crypt)
//    implementation("org.jetbrains.exposed:exposed-json:$exposedVersion")
    implementation(libs.h2)

    implementation(libs.hikariCP)
    implementation(libs.ehcache)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.insert.koin.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit5)

    implementation(libs.ktor.server.resources)
    implementation(libs.ktor.server.auto.head.response)
    implementation(libs.ktor.server.request.validation)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.serialization.kotlinx.json)
    testImplementation(libs.ktor.server.test.host)


    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.junit.jupiter)
}

tasks.test {
    useJUnitPlatform()
}