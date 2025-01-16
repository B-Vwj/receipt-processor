package com.bvwj.services

import com.bvwj.structures.Receipt
import com.bvwj.structures.toReceiptEntity
import java.util.UUID

class ReceiptService {
    fun add(receipt: Receipt): UUID {
        // validation

        require(receipt.items.isNotEmpty()) { EMPTY_ITEM_LIST_ERROR }

        // conversion to entity to be stored in db
//        val receiptEntity = receipt.toReceiptEntity()
//        val uuid = receiptRepository.add(receiptEntity)

        return UUID.randomUUID()
    }

    fun getPointsById(id: String): Int {
        // TODO: Implement service
        return 0
    }

    companion object {
        const val EMPTY_ITEM_LIST_ERROR = "Items list should not be empty."
    }
}