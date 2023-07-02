package com.binayshaw7777.readbud.ui.screens.image_screens

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binayshaw7777.readbud.model.RecognizedTextItem


class ImageViewModel() : ViewModel() {
    private val _bitmapList = MutableLiveData<List<Bitmap>>()
    val bitmapList: LiveData<List<Bitmap>> = _bitmapList

    private val _recognizedTextItemList = MutableLiveData<List<RecognizedTextItem>>()
    val recognizedTextItemList: LiveData<List<RecognizedTextItem>> = _recognizedTextItemList
//    val setOfRecognizedTextItem


    fun addBitmap(bitmap: Bitmap) {
        val currentList = _bitmapList.value.orEmpty().toMutableList()
        currentList.add(bitmap)
        _bitmapList.value = currentList
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