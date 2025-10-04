package top.suzhelan.ktor.entity

import kotlinx.serialization.Serializable

/**
 * 统一相应，入参对象
 */
@Serializable
data class Result<T>(
    val code: Int,
    val message: String,
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T? = null): Result<T> {
            return Result(code = 200, message = "success", data = data)
        }

        fun <T> error(code: Int, message: String): Result<T> {
            return Result(code = code, message = message)
        }
    }
}
