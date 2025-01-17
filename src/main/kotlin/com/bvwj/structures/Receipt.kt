package com.bvwj.structures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

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
internal fun toReceiptEntity(
    retailer: String,
    purchaseDate: LocalDate,
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