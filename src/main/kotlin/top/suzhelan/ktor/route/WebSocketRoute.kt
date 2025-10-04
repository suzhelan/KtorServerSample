package top.suzhelan.ktor.route

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import top.suzhelan.ktor.entity.SMessage


fun Route.websocketRoute() {
    //测试websocket
    webSocket("/ws") {
        //获取刚刚发送的消息
        val message = receiveDeserialized<SMessage>()
        println("server receive: $message")
        //循环给客户端发消息
        val messages = listOf(
            SMessage(1,"message-1"),
            SMessage(2,"message-2"),
            SMessage(3,"message-3"),
            SMessage(4,"message-4"),
            SMessage(5,"message-5"),
        )
        for (message in messages) {
            sendSerialized(message)
            delay(1000)
        }
        close(CloseReason(CloseReason.Codes.NORMAL, "All done"))
        println("server close")
    }
}