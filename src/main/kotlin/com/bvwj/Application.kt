package com.bvwj

import com.bvwj.controller.ReceiptRouter.receiptRouter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
//    embeddedServer(
//        factory = Netty,
//        port = 8080,
//        host = "127.0.0.1",
//        module = Application::module
//    ).start(wait = true)
}

fun Application.module() {
    install(Koin) { modules(myModules) }
    install(ContentNegotiation) { json( Json { ignoreUnknownKeys = true } ) }
    receiptRouter()
}