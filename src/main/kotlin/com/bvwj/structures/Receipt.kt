package com.bvwj.structures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
data class Receipt(
    val retailer: String,

    @SerialName("purchase_date")
    val purchaseDate: String,

    @SerialName("purchase_time")
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
    purchaseDate = this.purchaseDate.toLocalDate(),
    purchaseTime = this.purchaseTime,
    items = this.items,
    total = this.total
)

internal fun String.toLocalDate(): LocalDate {
    val parser = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDate.parse(this, parser)
}