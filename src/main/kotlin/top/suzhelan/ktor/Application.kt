package top.suzhelan.ktor

import io.ktor.server.application.*
import io.ktor.server.jetty.jakarta.*
import top.suzhelan.ktor.config.databaseConfigure
import top.suzhelan.ktor.config.pluginConfigure

/**
 * 运行处
 */
fun main(args: Array<String>) {
    EngineMain.main(args)
}

/**
 * 配置入口点
 */
fun Application.module() {
    //配置插件
    pluginConfigure()
    //配置数据库
    databaseConfigure()
    //配置路由
    configureRouting()
}
