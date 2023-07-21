package com.binayshaw7777.readbud.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecognizedTextItem(
    @PrimaryKey(autoGenerate = false)
    var index: Int = 0,
    var extractedText: String? = "",
    var thumbnail: Bitmap? = null
) : Parcelable