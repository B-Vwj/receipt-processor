package com.bvwj.services

import com.bvwj.repository.ReceiptRepository
import com.bvwj.services.ReceiptService.Companion.ErrorHandler.BLANK_PURCHASE_DATE_ERROR
import com.bvwj.services.ReceiptService.Companion.ErrorHandler.BLANK_PURCHASE_TIME_ERROR
import com.bvwj.services.ReceiptService.Companion.ErrorHandler.BLANK_RETAILER_ERROR
import com.bvwj.services.ReceiptService.Companion.ErrorHandler.BLANK_TOTAL_ERROR
import com.bvwj.services.ReceiptService.Companion.ErrorHandler.EMPTY_ITEM_LIST_ERROR
import com.bvwj.services.ReceiptService.Companion.ErrorHandler.INVALID_DATE_FORMAT
import com.bvwj.services.ReceiptService.Companion.ErrorHandler.INVALID_TIME_FORMAT
import com.bvwj.services.ReceiptService.Companion.ErrorHandler.INVALID_TOTAL_FORMAT
import com.bvwj.services.ReceiptService.Companion.ErrorHandler.POINTS_NOT_FOUND
import com.bvwj.services.ReceiptService.Companion.Validator.validDateString
import com.bvwj.services.ReceiptService.Companion.Validator.validTimeString
import com.bvwj.services.ReceiptService.Companion.Validator.validTotalString
import com.bvwj.structures.Receipt
import com.bvwj.structures.toReceiptEntity
import io.ktor.server.plugins.NotFoundException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID


class ReceiptService(private val receiptRepository: ReceiptRepository) {
    internal suspend fun add(receipt: Receipt): UUID {
        // validation
        // -- Check strings for empty or spaces "" or " "
        require(receipt.retailer.isNotBlank()) { BLANK_RETAILER_ERROR }
        require(receipt.purchaseDate.isNotBlank()) { BLANK_PURCHASE_DATE_ERROR }
        require(receipt.purchaseTime.isNotBlank()) { BLANK_PURCHASE_TIME_ERROR }
        require(receipt.total.isNotBlank()) { BLANK_TOTAL_ERROR }

        // -- Make sure item list is not empty (i.e. count > 0)
        require(receipt.items.isNotEmpty()) { EMPTY_ITEM_LIST_ERROR }

        // -- Make sure total is valid format (i.e. "XX.XX")
        require(validTotalString(receipt.total)) { INVALID_TOTAL_FORMAT }

        // -- Make sure time is correct format "XX:XX" (24H Format)
        require(validTimeString(receipt.purchaseTime)) { INVALID_TIME_FORMAT }

        // -- Make sure date is correct format "YYYY-MM-DD"
        require(validDateString(receipt.purchaseDate)) { INVALID_DATE_FORMAT }

        val parser = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(receipt.purchaseDate, parser)

        val receiptEntity = toReceiptEntity(
            retailer = receipt.retailer,
            purchaseDate = date,
            purchaseTime = receipt.purchaseTime,
            items = receipt.items,
            total = receipt.total
        )
        receiptRepository.add(receiptEntity)

        return receiptEntity.id
    }

    internal suspend fun getPointsById(uuid: UUID): Int {
        val receiptEntity = receiptRepository.getById(uuid = uuid)
        if (receiptEntity != null) {
            return receiptEntity.points
        } else {
            throw NotFoundException("$POINTS_NOT_FOUND: $uuid")
        }
    }

    companion object {
        object ErrorHandler {
            const val POINTS_NOT_FOUND = "No points found for "
            const val BLANK_RETAILER_ERROR = "Retailer field cannot be blank."
            const val BLANK_PURCHASE_DATE_ERROR = "Purchase date field cannot be blank."
            const val BLANK_PURCHASE_TIME_ERROR = "Purchase time field cannot be blank."
            const val BLANK_TOTAL_ERROR = "Total field cannot be blank."
            const val EMPTY_ITEM_LIST_ERROR = "Items list should not be empty."
            const val INVALID_TOTAL_FORMAT = "Invalid total format."
            const val INVALID_TIME_FORMAT = "Invalid time format."
            const val INVALID_DATE_FORMAT = "Invalid date format."
        }

        object Validator {
            private val TOTAL_FORMAT_REGEX_PATTERN = Regex(pattern = "^[0-9]\\d*\\.\\d{2}?\$")
            private val TIME_FORMAT_REGEX_PATTERN = Regex(pattern = "^([01]?\\d|2[0-3]):([0-5]?\\d)\$")
            private val DATE_FORMAT_REGEX_PATTERN = Regex(pattern = "^\\d{4}-\\d{2}-\\d{2}\$")

            internal fun validTotalString(total: String): Boolean {
                return total.matches(TOTAL_FORMAT_REGEX_PATTERN)
            }

            internal fun validTimeString(time: String): Boolean {
                return time.matches(TIME_FORMAT_REGEX_PATTERN)
            }

            internal fun validDateString(date: String): Boolean {
                return date.matches(DATE_FORMAT_REGEX_PATTERN)
            }
        }
    }
}