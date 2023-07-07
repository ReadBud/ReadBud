package com.binayshaw7777.readbud.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.binayshaw7777.readbud.data.repository.ScansRepository
import com.binayshaw7777.readbud.model.RecognizedTextItem
import com.binayshaw7777.readbud.model.Scans
import com.binayshaw7777.readbud.utils.getWordMeaningFromString
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ScansViewModel(private val application: Application) : AndroidViewModel(application) {
    private val scanRepository = ScansRepository(application)
    var listOfScans = scanRepository.getAllScansFromRoom().asLiveData()
    val onCompleteSaveIntoDB = MutableLiveData<Boolean>()
    val selectedScanDocument = MutableLiveData<Scans>()

    fun getScannedDocument() : Scans? {
        return selectedScanDocument.value
    }

    fun saveIntoDB(scanName: String, listOfPages: List<RecognizedTextItem>?) = viewModelScope.launch(Dispatchers.IO) {
        val pages: ArrayList<String> = ArrayList()
        if (listOfPages != null) {
            for (page in listOfPages) {
                val extractedText = page.extractedText.toString()
                pages.add(extractedText)
            }
        }
        val wordMeanings: HashMap<String, String> = getWordMeaningFromString(pages, application.applicationContext)
        val hashMapJson = Gson().toJson(wordMeanings)
        val scans = Scans(id = 0, scanName = scanName, pages = pages, wordMeaningsJson = hashMapJson)
        scanRepository.addScansToRoom(scans)
        onCompleteSaveIntoDB.postValue(true)
    }

    fun deleteAllScans() = viewModelScope.launch(Dispatchers.IO) {
        scanRepository.deleteAllScans()
    }

}