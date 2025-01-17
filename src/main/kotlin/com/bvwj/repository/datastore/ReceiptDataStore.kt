package com.bvwj.repository.datastore

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase

class ReceiptDataStore {
    internal fun setUpDatabase(): MongoDatabase {
        val uri = "mongodb://user:pass@localhost:27017/authSource=admin"

        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(uri))
            .build()

        val mongoClient = MongoClient.create(settings)
        return mongoClient.getDatabase("receipts")
    }
}