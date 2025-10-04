package top.suzhelan.ktor.entity

import kotlinx.serialization.Serializable

@Serializable
data class PluginInfo(
    val id: Int,
    val name: String,
    val description: String
)