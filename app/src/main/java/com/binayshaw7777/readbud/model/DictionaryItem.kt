package com.binayshaw7777.readbud.model

import com.google.gson.annotations.SerializedName


data class DictionaryItem(
    @SerializedName("word") var word: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("description") var description: String? = null
)

sealed class DictionaryRequestStatus {
    data class Success(val data: DictionaryItem) : DictionaryRequestStatus()
    data class Failure(val error: String) : DictionaryRequestStatus()
}