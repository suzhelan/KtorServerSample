package top.suzhelan.ktor.service

import top.suzhelan.ktor.entity.PluginInfo
import top.suzhelan.ktor.mapper.PluginInfoRepository

/**
 * springboot的三层设计原则
 * 1.接口入口controller
 * 2.业务逻辑service
 * 3.数据库查询repository/mapper
 *
 * 标准业务服务。查询，计算，业务都在service层做
 */
class PluginInfoService {

    val pluginInfoRepository = PluginInfoRepository()

    suspend fun queryAll(): List<PluginInfo> {
        return pluginInfoRepository.selectAll()
    }

    suspend fun add(pluginInfo: PluginInfo) {
        pluginInfoRepository.insert(pluginInfo)
    }
}