package com.bvwj

import com.bvwj.repository.ReceiptRepository
import com.bvwj.repository.datastore.ReceiptDataStore
import com.bvwj.services.ReceiptService
import org.koin.dsl.module

val myModules = module {
    single { ReceiptService(get()) }
    single { ReceiptRepository(get()) }
    single { ReceiptDataStore() }
}