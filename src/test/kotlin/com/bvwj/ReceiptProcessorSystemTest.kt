package com.bvwj

import com.bvwj.controller.IdResponse
import com.bvwj.controller.PointResponse
import com.bvwj.controller.RouterRoutes.ADD_RECEIPT
import com.bvwj.controller.RouterRoutes.GET_POINTS
import com.bvwj.structures.Item
import com.bvwj.structures.Receipt
import com.bvwj.structures.ReceiptEntity
import com.bvwj.structures.toReceiptEntity
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import io.mockk.MockKAnnotations
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent.inject
import java.util.UUID
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReceiptProcessorSystemTest {
    @Test
    fun getHealthEndpointReturnsUp() = testApplication {
        application { module() }
        val response = client.get("/health")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("up", response.bodyAsText())
    }

    @Test
    fun addMorningReceipt() = testApplication {
        val expectedUUID = morningReceipt.toReceiptEntity().id
        application { module() }

        val client = createClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val response = client.post(ADD_RECEIPT) {
            contentType(Json)
            setBody(morningReceipt)
        }

        assertEquals(HttpStatusCode.Created, response.status)
        assertEquals(IdResponse(id=expectedUUID), response.body<IdResponse>())
    }

    @Test
    fun addSimpleReceipt() = testApplication {
        val expectedUUID = simpleReceipt.toReceiptEntity().id
        application { module() }

        val client = createClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val response = client.post(ADD_RECEIPT) {
            contentType(Json)
            setBody(simpleReceipt)
        }

        assertEquals(HttpStatusCode.Created, response.status)
        assertEquals(IdResponse(id=expectedUUID), response.body<IdResponse>())
    }

    @Test
    fun getPointsById() = testApplication {
        application { module() }
        val entity = morningReceipt.toReceiptEntity()
        val expectedPoints = entity.points
        val uuid = entity.id

        val client = createClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val response = client.get("/receipts/${uuid}/points")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(PointResponse(points = expectedPoints), response.body<PointResponse>())
    }

    private val morningReceipt = Receipt(
        retailer = "Walgreens",
        purchaseDate = "2022-01-02",
        purchaseTime = "08:13",
        total = "2.65",
        items = listOf(
            Item(shortDescription = "Pepsi - 12-oz", price = "1.25"),
            Item(shortDescription = "Dasani", price = "1.40")
        )
    )

    private val simpleReceipt = Receipt(
        retailer = "Target",
        purchaseDate = "2022-01-02",
        purchaseTime = "13:13",
        total = "1.25",
        items = listOf(
            Item(shortDescription = "Pepsi - 12-oz", price = "1.25"),
        )
    )
}