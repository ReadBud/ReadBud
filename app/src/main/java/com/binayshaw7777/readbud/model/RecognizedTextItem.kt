package com.binayshaw7777.readbud.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("recognized_text_item")
data class RecognizedTextItem(
    @PrimaryKey(autoGenerate = false)
    var index: Int = 0,
    var extractedText: String? = "",
    @TypeConverters(Converters::class)
    var thumbnail: Bitmap? = null
) : Parcelable