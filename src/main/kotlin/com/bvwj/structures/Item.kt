package com.bvwj.structures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("short_description")
    val shortDescription: String,
    val price: String
)
