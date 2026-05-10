package com.weappsinc.watertracker.app.feature.weigh.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        WeighWeightLogEntity::class,
        WeighCompletedGoalEntity::class,
        WeighCompletedGoalLogEntity::class,
    ],
    version = 3,
    exportSchema = false
)
abstract class WeighWeightLogDatabase : RoomDatabase() {
    abstract fun weighWeightLogDao(): WeighWeightLogDao
    abstract fun weighCompletedGoalDao(): WeighCompletedGoalDao

    companion object {
        private const val DB_NAME = "weigh_weight_log.db"

        fun create(context: Context): WeighWeightLogDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                WeighWeightLogDatabase::class.java,
                DB_NAME
            )
                .addMigrations(WeighWeightLogDatabaseMigrations.MIGRATION_1_2)
                .addMigrations(WeighWeightLogDatabaseMigrations.MIGRATION_2_3)
                .build()
    }
}
