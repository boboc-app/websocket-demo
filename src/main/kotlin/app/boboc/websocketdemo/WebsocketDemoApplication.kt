package app.boboc.websocketdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import app.boboc.handler.CoroutineWebSocketHandler
import app.boboc.util.WebSocketSessionUtil.sendMessage

@SpringBootApplication
class WebsocketDemoApplication

fun main(args: Array<String>) {
    runApplication<WebsocketDemoApplication>(*args)
}

class WebfluxCoroutineHandler : CoroutineWebSocketHandler(){
    override suspend fun coroutineHandle(session: WebSocketSession, message: WebSocketMessage) {
        session.sendMessage("ECHO ${message.payloadAsText}")
    }

    override suspend fun onOpen(session: WebSocketSession) {
        println("OPEN ${session.id}")
    }

    override suspend fun onClose(session: WebSocketSession) {
        println("CLOSE ${session.id}")
    }
}

@Configuration
class WebSocketConfiguration{
    @Bean
    fun handlerMapping() : HandlerMapping {
        return SimpleUrlHandlerMapping( mapOf("test" to WebfluxCoroutineHandler()), -1 )
    }
}

