package com.bvwj.structures

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
internal fun Receipt.toReceiptEntity() = ReceiptEntity(
    retailer = this.retailer,
    purchaseDate = this.purchaseDate,
    purchaseTime = this.purchaseTime,
    items = this.items,
    total = this.total
)