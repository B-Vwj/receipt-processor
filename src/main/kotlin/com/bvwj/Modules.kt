package com.bvwj

import com.bvwj.repository.ReceiptRepository
import com.bvwj.services.ReceiptService
import com.bvwj.structures.ReceiptEntity
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.koin.dsl.module

val myModules = module {
    single { MongoClient.create("mongodb://user:pass@localhost:27017/receipts?authSource=admin") }
    single { get<MongoClient>().getDatabase("admin").getCollection<ReceiptEntity>("receipts") }
    single { ReceiptRepository(get()) }
    single { ReceiptService(get()) }
}