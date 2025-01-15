package com.bvwj

import io.ktor.server.application.Application
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        post("") {
            // TODO: Implement POST endpoint
        }

        get("") {
            // TODO: Implement GET endpoint
        }
    }
}
