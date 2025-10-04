package top.suzhelan.ktor

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.serialization.json.Json
import org.junit.Test
import top.suzhelan.ktor.config.HttpJsonDecoder
import top.suzhelan.ktor.entity.Result
import top.suzhelan.ktor.entity.SMessage
import kotlin.test.assertContains
import kotlin.test.assertEquals

/**
 * 最小单元测试,ktor client
 * 点击类或者方法左边的小箭头运行测试
 */
class ApplicationTest {

    /**
     * 基准测试状态
     */
    @Test
    fun testState() = testApplication {
        application {
            module()
        }
        val clientMsg = "This client send msg"
        val response = client.get("/api/state") {
            url {
                //add param
                parameter("clientMsg", clientMsg)
            }
        }
        val body = response.bodyAsText()
        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(body, clientMsg)
    }

    @Test
    fun testPost() = testApplication {
        application {
            module()
        }
        val id = "52930203"
        val response = client.post("/api/post") {
            setBody(
                //表单
                FormDataContent(Parameters.build {
                    append("id", id)
                })
            )
        }
        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(response.bodyAsText(), id)
    }

    /**
     * 测试异常请求
     */
    @Test
    fun testNotFoundRequest() = testApplication {
        application {
            module()
        }
        val response = client.get("/not-found") {
        }
        assertEquals(HttpStatusCode.NotFound, response.status)
    }


    /**
     * 测试序列化
     **/
    @Test
    fun testSerialization() = testApplication {
        application {
            module()
        }

        val response = commonClient().get("/restful-api")
        val result = response.body<Result<String>>()
        assertEquals(result.message, "success")
    }


    /**
     * 测试websocket
     */
    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun testWebSocket() = testApplication {
        application {
            module()
        }
        val client = commonClient()
        client.webSocket("/ws"){
            println("client connect")
            //测试发送
            val sendBody = SMessage(1, "this is client a test msg")
            send(converter!!.serialize(sendBody))
            //其他类似循环写法是等效的
            incoming.consumeAsFlow().collect { frame ->
                val message = converter!!.deserialize<SMessage>(frame)
                println("client receive: $message")
            }
            println("client disconnect")
        }
    }

}

private fun ApplicationTestBuilder.commonClient(): HttpClient = createClient {
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
    }
    install(ContentNegotiation) {
        json(HttpJsonDecoder)
    }
}
