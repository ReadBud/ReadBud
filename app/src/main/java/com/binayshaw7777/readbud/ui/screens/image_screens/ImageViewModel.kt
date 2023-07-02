package com.binayshaw7777.readbud.ui.screens.image_screens

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binayshaw7777.readbud.data.repository.ScansRepository
import com.binayshaw7777.readbud.model.RecognizedTextItem
import com.binayshaw7777.readbud.model.Scans
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ImageViewModel(application: Application) : AndroidViewModel(application) {

    private val scansRepository = ScansRepository(application)

    private val _recognizedTextItemList = MutableLiveData<List<RecognizedTextItem>>()
    val recognizedTextItemList: LiveData<List<RecognizedTextItem>> = _recognizedTextItemList

//    val onCompleteSaveIntoDB = MutableLiveData<Boolean>()

    fun addRecognizedTextItems(recognizedTextItem: RecognizedTextItem) {
        val currentList = _recognizedTextItemList.value.orEmpty().toMutableList()
        if (currentList.isEmpty().not()) {
            val previousScanText = currentList[currentList.size - 1].extractedText
            if (recognizedTextItem.extractedText == previousScanText) {
                return
            }
        }
        val lastIndex = if (currentList.isEmpty()) -1
        else
            currentList[currentList.size - 1].index

        val newIndex = lastIndex + 1
        recognizedTextItem.index = newIndex
        currentList.add(recognizedTextItem)
        _recognizedTextItemList.value = currentList
    }

    fun updateRecognizedItem(recognizedItem: RecognizedTextItem) {
        _recognizedTextItemList.value?.get(recognizedItem.index)?.extractedText =
            recognizedItem.extractedText
    }

//    fun saveIntoDB() = viewModelScope.launch(Dispatchers.IO) {
//        val listOfPages: ArrayList<RecognizedTextItem> =
//            (_recognizedTextItemList.value as ArrayList<RecognizedTextItem>?)!!
//        val scans = Scans(id = 0, listOfScans = listOfPages)
//        scansRepository.addScansToRoom(scans)
//        _recognizedTextItemList.postValue(ArrayList())
//        onCompleteSaveIntoDB.postValue(true)
//    }
}