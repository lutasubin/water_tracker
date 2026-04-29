package com.weappsinc.watertracker.app.core.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GenderSQLiteHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE user_gender (id INTEGER PRIMARY KEY, gender TEXT NOT NULL)")
        db.execSQL("CREATE TABLE user_age (id INTEGER PRIMARY KEY, age INTEGER NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) db.execSQL("CREATE TABLE user_age (id INTEGER PRIMARY KEY, age INTEGER NOT NULL)")
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

    companion object {
        private const val DB_NAME = "drink_water.db"
        private const val DB_VERSION = 2
    }
}
