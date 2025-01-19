package com.bvwj.repository

import com.bvwj.structures.Item
import com.bvwj.structures.Receipt
import com.bvwj.structures.ReceiptEntity
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID

class ReceiptRepository(private val mongoCollection: MongoCollection<ReceiptEntity>) {
    suspend fun add(receipt: ReceiptEntity) {
        mongoCollection.insertOne(receipt).also {
            println("Item added with id - ${it.insertedId}")
        }
    }

    suspend fun getById(uuid: UUID): ReceiptEntity? {
        val retrievedReceiptEntity = mongoCollection.find(
            Filters.eq(
                "id",
                uuid.toString()
            )
        )
            .firstOrNull()

        return retrievedReceiptEntity
    }
}