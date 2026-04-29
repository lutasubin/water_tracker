package com.weappsinc.watertracker.app.core.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GenderSQLiteHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE user_gender (id INTEGER PRIMARY KEY, gender TEXT NOT NULL)")
        db.execSQL("CREATE TABLE user_age (id INTEGER PRIMARY KEY, age INTEGER NOT NULL)")
        db.execSQL("CREATE TABLE user_tall (id INTEGER PRIMARY KEY, tall INTEGER NOT NULL)")
        db.execSQL("CREATE TABLE user_weight (id INTEGER PRIMARY KEY, weight INTEGER NOT NULL)")
        db.execSQL("CREATE TABLE user_exercise (id INTEGER PRIMARY KEY, level TEXT NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) db.execSQL("CREATE TABLE user_age (id INTEGER PRIMARY KEY, age INTEGER NOT NULL)")
        if (oldVersion < 3) {
            db.execSQL("CREATE TABLE user_tall (id INTEGER PRIMARY KEY, tall INTEGER NOT NULL)")
            db.execSQL("CREATE TABLE user_weight (id INTEGER PRIMARY KEY, weight INTEGER NOT NULL)")
        }
        if (oldVersion < 4) {
            db.execSQL("CREATE TABLE user_exercise (id INTEGER PRIMARY KEY, level TEXT NOT NULL)")
        }
    }

    fun saveGender(gender: String) {
        val values = ContentValues().apply {
            put("id", 1)
            put("gender", gender)
        }
        writableDatabase.insertWithOnConflict("user_gender", null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun getGender(): String? {
        readableDatabase.rawQuery("SELECT gender FROM user_gender WHERE id = 1", null).use { cursor ->
            return if (cursor.moveToFirst()) cursor.getString(0) else null
        }
    }

    fun saveAge(age: Int) {
        val values = ContentValues().apply {
            put("id", 1)
            put("age", age)
        }
        writableDatabase.insertWithOnConflict("user_age", null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun getAge(): Int? {
        readableDatabase.rawQuery("SELECT age FROM user_age WHERE id = 1", null).use { cursor ->
            return if (cursor.moveToFirst()) cursor.getInt(0) else null
        }
    }

    fun saveTall(tall: Int) {
        val values = ContentValues().apply {
            put("id", 1)
            put("tall", tall)
        }
        writableDatabase.insertWithOnConflict("user_tall", null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun getTall(): Int? {
        readableDatabase.rawQuery("SELECT tall FROM user_tall WHERE id = 1", null).use { cursor ->
            return if (cursor.moveToFirst()) cursor.getInt(0) else null
        }
    }

    fun saveWeight(weight: Int) {
        val values = ContentValues().apply {
            put("id", 1)
            put("weight", weight)
        }
        writableDatabase.insertWithOnConflict("user_weight", null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun getWeight(): Int? {
        readableDatabase.rawQuery("SELECT weight FROM user_weight WHERE id = 1", null).use { cursor ->
            return if (cursor.moveToFirst()) cursor.getInt(0) else null
        }
    }

    fun saveExerciseLevel(level: String) {
        val values = ContentValues().apply {
            put("id", 1)
            put("level", level)
        }
        writableDatabase.insertWithOnConflict("user_exercise", null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun getExerciseLevel(): String? {
        readableDatabase.rawQuery("SELECT level FROM user_exercise WHERE id = 1", null).use { cursor ->
            return if (cursor.moveToFirst()) cursor.getString(0) else null
        }
    }

    companion object {
        private const val DB_NAME = "drink_water.db"
        private const val DB_VERSION = 4
    }
}
