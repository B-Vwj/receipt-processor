package com.bvwj

import com.bvwj.repository.ReceiptRepository
import com.bvwj.services.ReceiptService
import com.bvwj.structures.ReceiptEntity
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.server.engine.ApplicationEnvironmentBuilder
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module

val myModules = module {
//    val applicationEnvironment = ApplicationEnvironmentBuilder().build()
//    val host = applicationEnvironment.config.propertyOrNull("ktor.deployment.host")?.getString() ?: "localhost"
//    val client = MongoClient.create("mongodb://user:pass@mongodb:27017/receipts?authSource=admin&directConnection=true")
//    val collection = runBlocking { client.getDatabase("admin").createCollection("receipts") }

    single { MongoClient.create("mongodb://user:pass@host.docker.internal:27017/receipts?authSource=admin&directConnection=true") }
    single { runBlocking { get<MongoClient>().getDatabase("admin").createCollection("receipts") } }
    single { get<MongoClient>().getDatabase("admin").getCollection<ReceiptEntity>("receipts") }
    single { ReceiptRepository(get()) }
    single { ReceiptService(get()) }
}