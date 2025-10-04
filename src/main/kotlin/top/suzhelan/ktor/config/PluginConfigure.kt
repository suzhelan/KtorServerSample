package top.suzhelan.ktor.config

import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.websocket.*
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

val HttpJsonDecoder = Json {
    //忽略未知jsonKey
    ignoreUnknownKeys = true
    //是否将null的属性写入json 默认true
    explicitNulls = true
    //是否使用默认值 默认false
    encodeDefaults = true
    //是否格式化json
    prettyPrint = true
    //宽容解析模式 可以解析不规范的json格式
    isLenient = false
    //允许特殊浮点数
    allowSpecialFloatingPointValues = true
    //是否允许结构化MapKey
    allowStructuredMapKeys = true
    //是否使用数组来表示多态结构
    useArrayPolymorphism = false
}
/**
 * 配置插件 这样就可以在client中使用json完成标准api请求
 */
fun Application.pluginConfigure() {
    //配置序列化
    install(ContentNegotiation) {
        json(json = HttpJsonDecoder)
    }
    install(WebSockets) {
        pingPeriod = 15.seconds
        timeout = 15.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter = KotlinxWebsocketSerializationConverter(HttpJsonDecoder)
    }
}

