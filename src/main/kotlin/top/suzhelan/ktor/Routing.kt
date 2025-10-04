package top.suzhelan.ktor

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import top.suzhelan.ktor.entity.Result
import top.suzhelan.ktor.route.websocketRoute

/**
 * 配置路由
 */
fun Application.configureRouting() {
    routing {
        //映射资源目录,第一个参数是远程访问的路径,第二个参数是本地的resources目录，这样就可以通过/content.txt直接访问到static文件夹的文件
        staticResources("/", "static")

        //根
        get("/") {
            //获取参数
            call.respondText("Hello World!")
        }

        //标准接口响应,自动序列化为json给前端
        get("/restful-api") {
            val message = "this is a restful api"
            call.respond(Result.success(message))
        }

        //统一定义路径,这样就可以通过/api/xxx访问
        route("/api") {
            //get带取参,/api/state?clientMsg=xxx
            get("/state") {
                val clientMsg = call.parameters["clientMsg"]
                call.respondText("Hello World! $clientMsg")
            }

            //post带表单参数
            post("/post") {
                val formContent = call.receiveParameters()
                val id = formContent["id"]
                call.respondText("Ok Your Id: $id")
            }

            //get带路径参数,/api/user/admin
            get("/user/{login}") {
                if (call.parameters["login"] == "admin") {
                    call.respondText("Your is Admin")
                } else {
                    call.respondText("Your is User")
                }
            }
        }
        //websocket接口示例
        websocketRoute()
    }
}
