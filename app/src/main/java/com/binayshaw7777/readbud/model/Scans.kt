package com.binayshaw7777.readbud.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "scans")
data class Scans(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var scanName: String,
    @TypeConverters(Converters::class)
    var pages: ArrayList<String>,
    var wordMeaningsJson: String
) : Parcelable