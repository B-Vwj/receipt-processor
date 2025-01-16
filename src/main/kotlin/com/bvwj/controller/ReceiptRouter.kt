package com.bvwj.controller

import com.bvwj.services.ReceiptService
import com.bvwj.structures.Receipt
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.util.UUID

fun Application.receiptRouter(receiptService: ReceiptService) {
    routing {
        post("/receipts/process") {
            val receipt = call.receive<Receipt>()
            val uuid = receiptService.add(receipt)

            call.respond(
                HttpStatusCode.Created,
                IdResponse(uuid)
            )
        }

        get("/receipts/{id}/points") {
            val id = call.parameters["id"]

            // grab receipt from ID
            val receipt: Int = receiptService.getPointsById(id)
            call.respond(
                HttpStatusCode.OK,
                receipt.items
            )
        }
    }
}

data class IdResponse(val id: UUID)