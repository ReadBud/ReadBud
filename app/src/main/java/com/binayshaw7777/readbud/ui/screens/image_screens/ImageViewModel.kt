package com.binayshaw7777.readbud.ui.screens.image_screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.binayshaw7777.readbud.model.RecognizedTextItem

class ImageViewModel(application: Application) : AndroidViewModel(application) {

    private val _recognizedTextItemList = MutableLiveData<List<RecognizedTextItem>>()
    val recognizedTextItemList: LiveData<List<RecognizedTextItem>> = _recognizedTextItemList

    fun clearAllRecognizedTextItems() : List<RecognizedTextItem>? {
        val currentList = _recognizedTextItemList.value
        _recognizedTextItemList.postValue(emptyList())
        return currentList
    }

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
}