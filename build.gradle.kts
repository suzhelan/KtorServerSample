
plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ktor)
}

group = "top.suzhelan"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.jetty.jakarta.EngineMain"
}

dependencies {
    //ktor server
    implementation(libs.ktor.server.jvm)
    implementation(libs.ktor.server.jetty)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.host.common)
    //序列化
    implementation(libs.ktor.serialization.json)
    //websocket
    implementation(libs.ktor.server.websockets)
    //ktorClient序列化
    implementation(libs.ktor.server.negotiation)

    implementation(libs.logback.classic)
    // pgSQL
    implementation(libs.postgresql)
    // exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.dao)
    // 与 exposed 搭配实现分页效果(应该)
    implementation(libs.ktor.server.status.pages)

    testImplementation(libs.ktor.client.negotiation)
    testImplementation(libs.ktor.client.websockets)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}