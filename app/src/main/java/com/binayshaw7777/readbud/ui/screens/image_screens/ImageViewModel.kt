package com.binayshaw7777.readbud.ui.screens.image_screens

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ImageViewModel() : ViewModel() {
    private val _bitmapList = MutableLiveData<List<Bitmap>>()
    val bitmapList: LiveData<List<Bitmap>> = _bitmapList


    fun addBitmap(bitmap: Bitmap) {
        val currentList = _bitmapList.value.orEmpty().toMutableList()
        currentList.add(bitmap)
        _bitmapList.value = currentList
    }
}