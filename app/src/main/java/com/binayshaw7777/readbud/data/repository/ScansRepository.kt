package com.binayshaw7777.readbud.data.repository

import android.app.Application
import com.binayshaw7777.readbud.data.dao.ScansDAO
import com.binayshaw7777.readbud.data.database.ScansDatabase
import com.binayshaw7777.readbud.model.Scans
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ScansRepository @Inject constructor(application: Application) {
    private var scansDAO: ScansDAO

    init {
        val database = ScansDatabase.getInstance(application)
        scansDAO = database.scansDao()
    }

    fun getAllScansFromRoom(): Flow<List<Scans>> = scansDAO.getAllScans()

    fun getScansByIdFromRoom(scanId: Int): Flow<Scans> = scansDAO.getScansById(scanId)

    suspend fun addScansToRoom(scan: Scans) = scansDAO.addScans(scan)

    suspend fun updateScansInRoom(scan: Scans) = scansDAO.updateScans(scan)

    suspend fun deleteScansFromRoom(scan: Scans) = scansDAO.deleteScans(scan)

    suspend fun deleteAllScans() = scansDAO.deleteAllScans()
}