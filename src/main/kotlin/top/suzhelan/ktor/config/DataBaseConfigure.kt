package top.suzhelan.ktor.config

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import top.suzhelan.ktor.route.crudRoute

/**
 * 配置数据库
 */
fun Application.databaseConfigure() {
    val enabled = environment.config.propertyOrNull("database.enabled")?.getString()?.toBoolean() ?: false
    if (enabled) {
        val url = environment.config.property("database.url").getString()
        val user = environment.config.property("database.user").getString()
        val password = environment.config.property("database.password").getString()
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        routing {
            crudRoute()
        }
    } else {
        log.warn("database not enabled")
    }
}