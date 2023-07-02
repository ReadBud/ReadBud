package com.binayshaw7777.readbud.model

import com.google.gson.annotations.SerializedName


data class DictionaryItem(
    @SerializedName("word") var word: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("description") var description: String? = null
)

sealed class RequestStatus {
    data class Success(val data: DictionaryItem) : RequestStatus()
    data class Failure(val error: String) : RequestStatus()
}