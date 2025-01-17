package com.bvwj.structures

import com.bvwj.controller.serializers.LocalDateSerializer
import com.bvwj.controller.serializers.UUIDSerializer
import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlin.math.roundToInt

@Serializable
data class ReceiptEntity(
    val retailer: String,

    @Serializable(with = LocalDateSerializer::class)
    @SerialName("purchase_date")
    val purchaseDate: LocalDate,

    @SerialName("purchase_time")
    val purchaseTime: String,

    val items: List<Item>,
    val total: String
) {
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.nameUUIDFromBytes("${retailer}.${purchaseDate}.${purchaseTime}.${items}.${total}".toByteArray())
    val points: Int = generatePoints(receipt = this)
}

/**
 * Convert [ReceiptEntity] to [Receipt]
 * @return  [Receipt]
 */
internal fun ReceiptEntity.receiptEntityToReceipt(
    retailer: String,
    purchaseDate: LocalDate,
    purchaseTime: String,
    items: List<Item>,
    total: String
) = Receipt(
    retailer = retailer,
    purchaseDate = convertDateToString(purchaseDate),
    purchaseTime = purchaseTime,
    items = items,
    total = total,
)

private fun convertDateToString(localDate: LocalDate): String {
    val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return localDate.format(pattern)
}

/**
 * Generate points from [Receipt]
 *
 */
private fun generatePoints(receipt: ReceiptEntity): Int {
    var pointsEarned = 0

    // These rules collectively define how many points should be awarded to a receipt.
    // -- One point for every alphanumeric character in the retailer name.
    pointsEarned += countAlphanumericCharacters(receipt.retailer)

    // -- 50 points if the total is a round dollar amount with no cents.
    if (checkIfTotalIsRounded(receipt.total)) pointsEarned += 50

    // -- 25 points if the total is a multiple of 0.25.
    if (checkIfTotalIsMultiple(receipt.total)) pointsEarned += 25

    // -- 5 points for every two items on the receipt.
    pointsEarned += receipt.items.size / 2 // odd integers being halved have their results rounded down

    // -- If the trimmed length of the item description is a multiple of 3,
    // -- multiply the price by 0.2 and round up to the nearest integer.
    // -- The result is the number of points earned.
    receipt.items.forEach { item ->
        val descriptionLength = item.shortDescription.trim().count()
        if (descriptionLength % 3 == 0) {
            val multipliedPrice = item.price.toDouble() * 0.2
            val roundedInt: Int = multipliedPrice.roundToInt()
            pointsEarned += roundedInt
        }
    }

    // -- 6 points if the day in the purchase date is odd.
    if (receipt.purchaseDate.dayOfMonth % 2 != 0) pointsEarned += 6

    // -- 10 points if the time of purchase is after 2:00pm and before 4:00pm.
    if (checkIfTimeIsBetweenCertainHours(receipt.purchaseTime)) pointsEarned += 10

    return pointsEarned
}

private fun checkIfTimeIsBetweenCertainHours(time: String): Boolean {
    val regex = Regex(pattern = "^(14|15|16):\\d{2}\$")
    return time.matches(regex)
}

private fun checkIfTotalIsRounded(total: String): Boolean {
    val regex = Regex(pattern = "^[0-9]\\d*\\.00\$")
    return total.matches(regex)
}

private fun checkIfTotalIsMultiple(total: String): Boolean {
    val regex = Regex(pattern = "\\d{2}\\.(00|25|50|75)")
    return total.matches(regex)
}

/**
 * Returns count of alphanumeric characters are in the retailer name.
 * @param  retailer  retailer name as [String]
 * @return [Int]  count of alphanumeric characters present
 */
private fun countAlphanumericCharacters(retailer: String): Int {
    var numberOfAlphanumericChars = 0
    retailer.toCharArray().forEach { char ->
        if (char.isLetter()) numberOfAlphanumericChars++
    }
    return numberOfAlphanumericChars
}
