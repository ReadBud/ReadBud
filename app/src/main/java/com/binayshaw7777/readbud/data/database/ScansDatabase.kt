package com.binayshaw7777.readbud.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.binayshaw7777.readbud.data.dao.ScansDAO
import com.binayshaw7777.readbud.model.Converters
import com.binayshaw7777.readbud.model.RecognizedTextItem
import com.binayshaw7777.readbud.model.Scans

@Database(entities = [Scans::class], version = 2)
@TypeConverters(Converters::class)
abstract class ScansDatabase : RoomDatabase() {

    abstract fun scansDao() : ScansDAO

    companion object {
        private var INSTANCE: ScansDatabase? = null
        fun getInstance(context: Context): ScansDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ScansDatabase::class.java,
                        "scans_database"
                    )
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}