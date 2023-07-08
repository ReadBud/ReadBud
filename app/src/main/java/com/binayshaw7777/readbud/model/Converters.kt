package com.binayshaw7777.readbud.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken


class Converters {

    @TypeConverter
    fun fromJson(value: String): HashMap<String, String> {
        val mapType = object : TypeToken<HashMap<String, String>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun toJson(map: HashMap<String, String>): String {
        return Gson().toJson(map)
    }


    @TypeConverter
    fun restoreList(listOfString: String): ArrayList<String> {
        return Gson().fromJson(listOfString, object : TypeToken<ArrayList<String?>?>() {}.type)
    }

    @TypeConverter
    fun saveList(listOfString: ArrayList<String>): String {
        return Gson().toJson(listOfString)
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