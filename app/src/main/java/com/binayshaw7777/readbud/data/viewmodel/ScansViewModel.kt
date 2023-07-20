package com.binayshaw7777.readbud.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.binayshaw7777.readbud.data.repository.ScansRepository
import com.binayshaw7777.readbud.model.RecognizedTextItem
import com.binayshaw7777.readbud.model.Scans
import com.binayshaw7777.readbud.utils.getJargonWords
import com.binayshaw7777.readbud.utils.getWordMeaningFromString
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScansViewModel @Inject constructor(
    private val application: Application,
    private val scanRepository: ScansRepository
) : AndroidViewModel(application) {

    var listOfScans = scanRepository.getAllScansFromRoom().asLiveData()
    val onCompleteSaveIntoDB = MutableLiveData<Boolean>()

    private val _scanItemLiveData = MutableLiveData<Scans>()
    val scanItemLiveData: LiveData<Scans> get() = _scanItemLiveData

    init {
        _scanItemLiveData.postValue(Scans(0, "null", arrayListOf(), "{}"))
    }

    fun deleteScanItem(scan: Scans) = viewModelScope.launch(Dispatchers.IO) {
        scanRepository.deleteScansFromRoom(scan)
    }

    fun saveIntoDB(scanName: String, listOfPages: List<RecognizedTextItem>?) =
        viewModelScope.launch(Dispatchers.IO) {
            val pages: ArrayList<String> = ArrayList()
            if (listOfPages != null) {
                for (page in listOfPages) {
                    val extractedText = page.extractedText.toString()
                    pages.add(extractedText)
                }
            }
            val jargonWords: List<String> = getJargonWords(pages, application.applicationContext)
            val wordMeanings: HashMap<String, String> =
                getWordMeaningFromString(jargonWords, application.applicationContext)
            val hashMapJson = Gson().toJson(wordMeanings)
            val scans =
                Scans(id = 0, scanName = scanName, pages = pages, wordMeaningsJson = hashMapJson)
            scanRepository.addScansToRoom(scans)
            onCompleteSaveIntoDB.postValue(true)
        }

    fun getScanById(scanId: Int) = viewModelScope.launch(Dispatchers.IO) {
        scanRepository.getScansByIdFromRoom(scanId).collect {
            _scanItemLiveData.postValue(it)
        }
    }

    fun deleteAllScans() = viewModelScope.launch(Dispatchers.IO) {
        scanRepository.deleteAllScans()
    }

}