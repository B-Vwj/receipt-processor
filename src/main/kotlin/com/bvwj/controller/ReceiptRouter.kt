package com.bvwj.controller

import com.bvwj.controller.RouterRoutes.ADD_RECEIPT
import com.bvwj.controller.RouterRoutes.GET_POINTS
import com.bvwj.controller.serializers.UUIDSerializer
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
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject
import java.util.UUID

object ReceiptRouter {
    fun Application.receiptRouter() {
        val receiptService: ReceiptService by inject()

        routing {
            post(ADD_RECEIPT) {
                val receipt = call.receive<Receipt>()
                receiptService.add(receipt).let { uuid ->
                    call.respond(HttpStatusCode.Created, IdResponse(uuid))
                }
            }

            get(GET_POINTS) {
                val uuid: UUID = call.parameters.getOrFail(name = "id").let { UUID.fromString(it) }
                receiptService.getPointsById(uuid).let { points ->
                    call.respond(HttpStatusCode.OK, PointResponse(points))
                }
            }

            get("/health") {
                call.respond(HttpStatusCode.OK, "up")
            }
        }
    }
}

object RouterRoutes {
    const val ADD_RECEIPT = "/receipts/process"
    const val GET_POINTS = "/receipts/{id}/points"
}

@Serializable
data class IdResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID
)

@Serializable
data class PointResponse(
    val points: Int
)