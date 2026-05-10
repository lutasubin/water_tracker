package com.weappsinc.watertracker.app.feature.weigh.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal object WeighWeightLogDatabaseMigrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `weigh_completed_goal` (
                  `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                  `targetWeightKg` REAL NOT NULL,
                  `journeyStartWeightKg` REAL NOT NULL,
                  `achievedBodyWeightKg` REAL NOT NULL,
                  `completedAtEpochDay` INTEGER NOT NULL,
                  `completedAtMs` INTEGER NOT NULL
                )
                """.trimIndent()
            )
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `weigh_completed_goal_log` (
                  `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                  `goalId` INTEGER NOT NULL,
                  `epochDay` INTEGER NOT NULL,
                  `weightKg` REAL NOT NULL,
                  `recordedAtMs` INTEGER NOT NULL,
                  FOREIGN KEY(`goalId`) REFERENCES `weigh_completed_goal`(`id`) ON DELETE CASCADE
                )
                """.trimIndent()
            )
            db.execSQL(
                """
                CREATE INDEX IF NOT EXISTS `index_weigh_completed_goal_log_goalId`
                ON `weigh_completed_goal_log` (`goalId`)
                """.trimIndent()
            )
        }
    }
}
