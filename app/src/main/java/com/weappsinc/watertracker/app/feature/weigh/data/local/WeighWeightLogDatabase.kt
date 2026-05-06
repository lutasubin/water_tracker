package com.weappsinc.watertracker.app.feature.weigh.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [WeighWeightLogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeighWeightLogDatabase : RoomDatabase() {
    abstract fun weighWeightLogDao(): WeighWeightLogDao

    companion object {
        private const val DB_NAME = "weigh_weight_log.db"

        fun create(context: Context): WeighWeightLogDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                WeighWeightLogDatabase::class.java,
                DB_NAME
            ).build()
    }
}
