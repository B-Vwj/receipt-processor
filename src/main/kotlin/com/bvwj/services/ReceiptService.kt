package com.bvwj.services

import com.bvwj.repository.ReceiptRepository
import com.bvwj.structures.Receipt
import com.bvwj.structures.toReceiptEntity
import java.util.UUID

class ReceiptService(private val receiptRepository: ReceiptRepository) {
    fun add(receipt: Receipt): UUID {
        // validation
        // -- Check strings for empty or spaces "" or " "
        require(receipt.retailer.isNotBlank()) { BLANK_RETAILER_ERROR }
        require(receipt.purchaseDate.isNotBlank()) { BLANK_PURCHASE_DATE_ERROR }
        require(receipt.purchaseTime.isNotBlank()){ BLANK_PURCHASE_TIME_ERROR }
        require(receipt.total.isNotBlank()){ BLANK_TOTAL_ERROR }

        // -- Make sure item list is not empty (i.e. count > 0)
        require(receipt.items.isNotEmpty()) { EMPTY_ITEM_LIST_ERROR }

        // -- Make sure time is correct format "XX:XX" (24H Format)
//        requireWellFormedPurchaseTime()

        // -- Make sure date is correct format "MM-DD-YYYY"
//        requireWellFormedPurchaseDate()

        val receiptEntity = receipt.toReceiptEntity()
        receiptRepository.add(receiptEntity)

        return receiptEntity.id
    }

    fun getPointsById(uuid: UUID): Int {
        val receiptEntity = receiptRepository.getById(uuid = uuid)
        return receiptEntity.points
    }

    companion object {
        const val BLANK_RETAILER_ERROR = "Retailer field cannot be blank."
        const val BLANK_PURCHASE_DATE_ERROR = "Purchase date field cannot be blank."
        const val BLANK_PURCHASE_TIME_ERROR = "Purchase time field cannot be blank."
        const val BLANK_TOTAL_ERROR = "Total field cannot be blank."
        const val EMPTY_ITEM_LIST_ERROR = "Items list should not be empty."
    }
}