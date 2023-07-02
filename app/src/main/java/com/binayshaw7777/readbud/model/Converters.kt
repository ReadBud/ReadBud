package com.binayshaw7777.readbud.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import java.io.ByteArrayOutputStream


class Converters {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray? {
        val outputStream = ByteArrayOutputStream()
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, outputStream)
            return outputStream.toByteArray()
        }
        return null
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap? {
        return if (byteArray != null)
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        else null
    }

    @TypeConverter
    fun listOfRecognizedTextToString(value: List<RecognizedTextItem>): String {
        return Gson().toJson(value)

    }

    @TypeConverter
    fun stringTextToListOfRecognized(value: String): ArrayList<RecognizedTextItem> {
        val list = Gson().fromJson(value, Array<RecognizedTextItem>::class.java).toList()
        val arrayListOfRecognizedTextItem: ArrayList<RecognizedTextItem> = ArrayList()
        arrayListOfRecognizedTextItem.addAll(list)
        return arrayListOfRecognizedTextItem
    }
}