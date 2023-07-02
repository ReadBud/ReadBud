package com.binayshaw7777.readbud.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.binayshaw7777.readbud.data.repository.ScansRepository
import com.binayshaw7777.readbud.model.Scans
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ScansViewModel(application: Application) : AndroidViewModel(application) {

    private val scanRepository = ScansRepository(application)
    var listOfScans = MutableLiveData<MutableList<Scans>>()

    fun getAllScans() = viewModelScope.launch(Dispatchers.IO) {
        scanRepository.getAllScansFromRoom().collect { scans ->
            listOfScans.value?.clear()
            for (scan in scans) {
                val current = listOfScans.value ?: mutableListOf()
                current.add(scan)
                listOfScans.postValue(current)
            }
        }
    }

    fun deleteAllScans() = viewModelScope.launch(Dispatchers.IO) {
        scanRepository.deleteAllScans()
    }

}