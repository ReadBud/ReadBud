package com.binayshaw7777.readbud.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class DictionaryItem(
    @SerializedName("_id") var Id: Int? = null,
    @SerializedName("antonyms") var antonyms: String? = null,
    @SerializedName("en_definition") var enDefinition: String? = null,
    @SerializedName("en_word") var enWord: String? = null,
    @SerializedName("example") var example: String? = null,
    @SerializedName("synonyms") var synonyms: String? = null
) : Parcelable