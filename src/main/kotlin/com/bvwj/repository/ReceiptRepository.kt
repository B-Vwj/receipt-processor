package com.bvwj.repository

import com.bvwj.repository.datastore.ReceiptDataStore
import com.bvwj.structures.ReceiptEntity
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID

class ReceiptRepository(receiptDataStore: ReceiptDataStore) {
    private val mongoDatabase: MongoCollection<ReceiptEntity> =
        receiptDataStore
            .setUpDatabase()
            .getCollection<ReceiptEntity>("receipts")

    suspend fun add(receipt: ReceiptEntity) {
        mongoDatabase.insertOne(receipt).also {
            println("Item added with id - ${it.insertedId}")
        }
    }

    suspend fun getById(uuid: UUID): ReceiptEntity? {
        val retrievedReceiptEntity = mongoDatabase.find(
            Filters.eq(
                "id",
                uuid.toString()
            )
        )
            .firstOrNull()

        return retrievedReceiptEntity
    }
}