package com.binayshaw7777.readbud.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.binayshaw7777.readbud.model.RecognizedTextItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageSharedViewModel(application: Application) : AndroidViewModel(application) {

    private val _recognizedTextItemList = MutableLiveData<List<RecognizedTextItem>>()
    val recognizedTextItemList: LiveData<List<RecognizedTextItem>> = _recognizedTextItemList

    fun clearAllRecognizedTextItems(): List<RecognizedTextItem>? {
        val currentList = _recognizedTextItemList.value
        _recognizedTextItemList.postValue(emptyList())
        return currentList
    }

    fun removeItemFromIndex(index: Int) = viewModelScope.launch(Dispatchers.IO) {
        val currentList = arrayListOf<RecognizedTextItem>()
        currentList.addAll(_recognizedTextItemList.value!!)
        currentList.removeIf { it.index == index }
        _recognizedTextItemList.postValue(currentList)
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