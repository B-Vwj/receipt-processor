package com.bvwj.controller

import com.bvwj.services.ReceiptService
import com.bvwj.structures.Receipt
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.util.getOrFail
import org.koin.ktor.ext.inject
import java.util.UUID

object ReceiptRouter {
    fun Application.receiptRouter() {
        val receiptService: ReceiptService by inject()

        routing {
            post("/receipts/process") {
                val receipt = call.receive<Receipt>()
                receiptService.add(receipt).let { uuid ->
                    call.respond(HttpStatusCode.Created, IdResponse(uuid))
                }
            }

            get("/receipts/{id}/points") {
                val uuid: UUID = call.parameters.getOrFail(name = "id").let { UUID.fromString(it) }
                receiptService.getPointsById(uuid).let { points ->
                    call.respond(HttpStatusCode.OK, PointResponse(points))
                }
            }
        }
    }
}

data class IdResponse(val id: UUID)

data class PointResponse(val points: Int)