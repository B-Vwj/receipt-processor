package com.bvwj.repository

import com.bvwj.structures.ReceiptEntity
import java.time.LocalDateTime
import java.util.UUID

class ReceiptRepository {
    fun add(receipt: ReceiptEntity) {
        // TODO: Implement datastore method
    }

    fun getById(uuid: UUID): ReceiptEntity {
        // TODO: Implement datastore method

        return ReceiptEntity(
            retailer = "",
            purchaseDate = LocalDateTime.now(),
            purchaseTime = "",
            items = listOf(),
            total = ""
        )
    }
}