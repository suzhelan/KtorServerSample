package top.suzhelan.ktor.entity

import kotlinx.serialization.Serializable


@Serializable
data class SMessage(
    val id: Int,
    val content: String,
)