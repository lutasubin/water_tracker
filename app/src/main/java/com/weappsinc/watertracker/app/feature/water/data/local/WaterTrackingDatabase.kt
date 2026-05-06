package com.weappsinc.watertracker.app.feature.water.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [DailyWaterTotalEntity::class, WaterIntakeLogEntity::class],
    version = 2,
    exportSchema = false
)
abstract class WaterTrackingDatabase : RoomDatabase() {
    abstract fun waterIntakeDao(): WaterIntakeDao

    companion object {
        private const val DB_NAME = "water_tracking.db"

        /** Thêm bảng log uống nước (giữ dữ liệu daily_water_total). */
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `water_intake_log` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `epoch_day` INTEGER NOT NULL,
                        `timestamp_ms` INTEGER NOT NULL,
                        `amount_ml` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        fun create(context: Context): WaterTrackingDatabase =
            Room.databaseBuilder(context.applicationContext, WaterTrackingDatabase::class.java, DB_NAME)
                .addMigrations(MIGRATION_1_2)
                .build()
    }
}
