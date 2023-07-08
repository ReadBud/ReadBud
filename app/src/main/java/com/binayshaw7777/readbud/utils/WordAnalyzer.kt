package com.binayshaw7777.readbud.utils

import android.content.Context
import android.content.res.AssetManager
import com.binayshaw7777.readbud.model.DictionaryItem
import com.binayshaw7777.readbud.model.WordsData
import com.binayshaw7777.readbud.utils.Constants.COMMON_WORDS
import com.binayshaw7777.readbud.utils.Constants.DICTIONARY
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset


fun parseJsonFile(assetManager: AssetManager): List<DictionaryItem>? {
    val fileName = DICTIONARY

    return try {
        val inputStream: InputStream = assetManager.open(fileName)
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val gson = Gson()
        val dictionaryItems: List<DictionaryItem> =
            gson.fromJson(jsonString, Array<DictionaryItem>::class.java).toList()

        dictionaryItems
    } catch (exception: JsonSyntaxException) {
        Logger.debug("An JsonSyntaxException occurred: $exception")
        null
    } catch (exception: IOException) {
        Logger.debug("An IOException occurred: $exception")
        null
    } catch (exception: Exception) {
        Logger.debug("An exception occurred: $exception")
        null
    }
}

fun returnMapFromDictionary(assetManager: AssetManager): HashMap<String, String> {
    val listOfDictionaryItem: List<DictionaryItem>? = parseJsonFile(assetManager)
    val map: HashMap<String, String> = hashMapOf()
    listOfDictionaryItem?.let {
        for (dictionaryItem in it) {
            map[dictionaryItem.enWord.toString().lowercase()] =
                dictionaryItem.enDefinition.toString()
        }
    }
    return map
}

suspend fun getJargonWords(
    pages: List<String>,
    context: Context
): ArrayList<String> {
    val listOfJargonWords: ArrayList<String> = arrayListOf()
    withContext(Dispatchers.IO) {
        val commonWordsList: List<String> = fetchWordListFromJSON(context, COMMON_WORDS)
        listOfJargonWords.addAll(findJargonWords(pages, commonWordsList))
    }
    return listOfJargonWords
}


suspend fun getWordMeaningFromString(
    jargonWords: List<String>,
    context: Context
): HashMap<String, String> {
    val jargonWordsCaught: HashMap<String, String> = HashMap()
    withContext(Dispatchers.IO) {
        val dictionary: HashMap<String, String> = returnMapFromDictionary(context.assets)
        Logger.debug("Jargon words from String: $jargonWords")

        for (word in jargonWords) {
            if (dictionary.containsKey(word.lowercase())) {
                jargonWordsCaught[word.lowercase()] = dictionary.getValue(word.lowercase())
            }
        }
        Logger.debug("Jargon words with meaning caught: $jargonWordsCaught")
    }
    return jargonWordsCaught
}

fun findJargonWords(pages: List<String>, commonWordsList: List<String>): List<String> {
    val jargonWordsList = mutableListOf<String>()
    for (paragraph in pages) {
        val words = paragraph.split("\\s+".toRegex()) // Split paragraph into individual words

        for (word in words) {
            val normalizedWord =
                word.replace("[^A-Za-z]".toRegex(), "") // Remove non-alphabetic characters
            if (normalizedWord.isNotEmpty() && normalizedWord.lowercase() !in commonWordsList) {
                jargonWordsList.add(normalizedWord)
            }
        }
    }
    return jargonWordsList
}

fun loadJSONFromAsset(context: Context, fileName: String): String? {
    var json: String? = null
    try {
        val inputStream: InputStream = context.assets.open(fileName)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer, Charset.forName("UTF-8"))
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return json
}

fun fetchWordListFromJSON(context: Context, fileName: String): List<String> {
    val jsonString = loadJSONFromAsset(context, fileName)
    return try {
        Gson().fromJson(jsonString, WordsData::class.java).Words
    } catch (e: Exception) {
        Logger.debug("Exception: $e")
        ArrayList()
    }
}

fun jsonToHashMap(jsonString: String): HashMap<String, String> {
    val type = object : TypeToken<HashMap<String, String>>() {}.type
    return Gson().fromJson(jsonString, type)
}