package com.binayshaw7777.readbud.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


//@Parcelize
//@Entity(tableName = "words")
//data class DictionaryItem(
//    @PrimaryKey(autoGenerate = false)
//    @ColumnInfo(name = "_id")
//    var _id: Int,
//    @ColumnInfo(name = "en_word")
//    var en_word: String,
//    @ColumnInfo(name = "en_definition")
//    var en_definition: String,
//    @ColumnInfo(name = "example")
//    var example: String,
//    @ColumnInfo(name = "synonyms")
//    var synonyms: String,
//    @ColumnInfo(name = "antonyms")
//    var antonyms: String
//) : Parcelable

@Parcelize
data class DictionaryItem(
    @SerializedName("_id") var Id: Int? = null,
    @SerializedName("antonyms") var antonyms: String? = null,
    @SerializedName("en_definition") var enDefinition: String? = null,
    @SerializedName("en_word") var enWord: String? = null,
    @SerializedName("example") var example: String? = null,
    @SerializedName("synonyms") var synonyms: String? = null
) : Parcelable