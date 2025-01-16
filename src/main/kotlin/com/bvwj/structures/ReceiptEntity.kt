package com.bvwj.structures

import io.ktor.utils.io.core.toByteArray
import java.util.UUID

data class ReceiptEntity(
    val retailer: String,
    val purchaseDate: String,
    val purchaseTime: String,
    val items: List<Item>,
    val total: String
) {
    val id: UUID = UUID.nameUUIDFromBytes("${retailer}.${purchaseDate}.${purchaseTime}.${items}.${total}".toByteArray())
    val points: Int = generatePoints(receipt = this)
}

/**
 * Convert [ReceiptEntity] to [Receipt]
 * @return  [Receipt]
 */
internal fun ReceiptEntity.receiptEntityToReceipt() = Receipt(
    retailer = this.retailer,
    purchaseDate = this.purchaseDate,
    purchaseTime = this.purchaseTime,
    items = this.items,
    total = this.total,
)

/**
 * Generate points from [Receipt]
 *
 */
private fun generatePoints(receipt: ReceiptEntity): Int {
    val points = 0

    // TODO: Implement logic

    return points
}