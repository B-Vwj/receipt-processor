package structures

import com.bvwj.structures.Item
import com.bvwj.structures.Receipt

object TestData {
    val RECEIPT_MORNING_TEST_DATA = Receipt(
        retailer = "Walgreens",
        purchaseDate = "2022-01-02",
        purchaseTime = "08:13",
        total = "2.65",
        items = listOf(
            Item(shortDescription = "Pepsi - 12-oz", price = "1.25"),
            Item(shortDescription = "Dasani", price = "1.40")
        )
    )

    val RECEIPT_SIMPLE_TEST_DATA = Receipt(
        retailer = "Target",
        purchaseDate = "2022-01-02",
        purchaseTime = "13:13",
        total = "1.25",
        items = listOf(
            Item(shortDescription = "Pepsi - 12-oz", price = "1.25"),
        )
    )
}