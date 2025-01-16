package com.bvwj

import com.bvwj.controller.ReceiptRouter
import com.bvwj.services.ReceiptService
import org.koin.dsl.module

class Modules {
    val appModules = module {
        single<ReceiptRouter> { ReceiptRouter }
    }
}