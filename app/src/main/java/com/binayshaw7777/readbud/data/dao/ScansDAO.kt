package com.binayshaw7777.readbud.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.binayshaw7777.readbud.model.Scans
import kotlinx.coroutines.flow.Flow


@Dao
interface ScansDAO {

    @Query("SELECT * FROM scans ORDER BY id ASC")
    fun getAllScans(): Flow<List<Scans>>

    @Query("SELECT * FROM scans WHERE id = :scansId")
    fun getScansById(scansId: Int): Flow<Scans>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addScans(scansModel: Scans)

    @Update
    suspend fun updateScans(scansModel: Scans)

    @Delete
    suspend fun deleteScans(scansModel: Scans)

    @Query("DELETE FROM scans")
    suspend fun deleteAllScans()

}