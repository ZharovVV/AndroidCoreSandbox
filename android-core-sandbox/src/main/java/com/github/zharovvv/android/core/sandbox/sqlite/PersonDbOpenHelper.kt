package com.github.zharovvv.android.core.sandbox.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDao.Companion.TABLE_NAME
import com.github.zharovvv.android.core.sandbox.sqlite.PersonDatabase.Companion.DATABASE_NAME

class PersonDbOpenHelper(
    context: Context?,
    version: Int
) : SQLiteOpenHelper(context, DATABASE_NAME, null, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}