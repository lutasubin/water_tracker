package com.weappsinc.watertracker.app.feature.water.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DailyWaterTotalEntity::class], version = 1, exportSchema = false)
abstract class WaterTrackingDatabase : RoomDatabase() {
    abstract fun waterIntakeDao(): WaterIntakeDao

    companion object {
        private const val DB_NAME = "water_tracking.db"

        fun create(context: Context): WaterTrackingDatabase =
            Room.databaseBuilder(context.applicationContext, WaterTrackingDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
