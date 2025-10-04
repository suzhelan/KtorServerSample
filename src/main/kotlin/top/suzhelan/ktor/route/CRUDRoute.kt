package top.suzhelan.ktor.route

import io.ktor.server.response.*
import io.ktor.server.routing.*
import top.suzhelan.ktor.entity.PluginInfo
import top.suzhelan.ktor.service.PluginInfoService
import java.time.LocalDateTime

/**
 * 标准数据库查询
 */
fun Route.crudRoute() {
    val pluginInfoService = PluginInfoService()
    //查询数据库
    get("/queryAll") {
        val pluginInfoList = pluginInfoService.queryAll()
        call.respond(pluginInfoList)
    }
    //插入数据库
    get("/add") {
        val pluginInfo = PluginInfo(1, "pluginName", "this call api created:" + LocalDateTime.now())
        pluginInfoService.add(pluginInfo)
        call.respond("add")
    }
}