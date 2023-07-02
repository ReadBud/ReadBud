package com.binayshaw7777.readbud.utils

import android.content.res.AssetManager
import com.binayshaw7777.readbud.model.DictionaryItem
import com.binayshaw7777.readbud.model.RequestStatus
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.InputStream

fun parseJsonFile(fileName: String, assetManager: AssetManager): RequestStatus {
    return try {
        val inputStream: InputStream = assetManager.open(fileName)
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val gson = Gson()
        val dataClass = gson.fromJson(jsonString, DictionaryItem::class.java)
        RequestStatus.Success(dataClass)
    } catch (exception: JsonSyntaxException) {
        RequestStatus.Failure("Invalid JSON format")
    } catch (exception: Exception) {
        RequestStatus.Failure(exception.message ?: "Unknown error occurred")
    }
}