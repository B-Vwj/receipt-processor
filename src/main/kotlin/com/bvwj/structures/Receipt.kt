package com.bvwj.structures

import java.time.LocalDateTime

data class Receipt(
    val retailer: String,
    val purchaseDate: String,
    val purchaseTime: String,
    val items: List<Item>,
    val total: String
)

/**
 * Convert [Receipt] into [ReceiptEntity]
 * @return  [ReceiptEntity]
 */
internal fun toReceiptEntity(
    retailer: String,
    purchaseDate: LocalDateTime,
    purchaseTime: String,
    items: List<Item>,
    total: String
) = ReceiptEntity(
    retailer = retailer,
    purchaseDate = purchaseDate,
    purchaseTime = purchaseTime,
    items = items,
    total = total
)